package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.*;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.criteria.core.finance.CompteCriteria;
import ma.zyn.app.dao.facade.core.finance.CompteDao;
import ma.zyn.app.dao.specification.core.finance.CompteSpecification;
import ma.zyn.app.service.facade.admin.finance.*;
import ma.zyn.app.service.facade.admin.locataire.TransactionAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ma.zyn.app.zynerator.util.ListUtil.*;

@Service
public class CompteAdminServiceImpl implements CompteAdminService {

    @Autowired
    private CompteAdminAdminService compteAdminAdminService;
    @Autowired
    private BanqueAdminServiceImpl banqueAdminService;
    @Autowired
    private CaisseAdminServiceImpl caisseAdminService;
    @Autowired
    private CompteInstantaneeAdminService compteInstantaneeAdminService;
    @Autowired
    private CompteChargeAdminService compteChargeAdminService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Compte update(Compte t) {
        Compte loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Compte.class.getSimpleName(), t.getId().toString()});
        } else {
            updateWithAssociatedLists(t);
            Compte saved = dao.save(t);
            if(t.getBanque()!=null){
                updateBanque(t.getBanque(),saved);
            }
            if(t.getCaisse()!=null){
                updateCaisse(t.getCaisse(),saved);
            }
            CompteAdmin byId = compteAdminAdminService.findAdmin();
            byId.updateSolde();
            compteAdminAdminService.update(byId);
            return loadedItem;
        }
    }

    private void updateCaisse(Caisse caisse, Compte saved) {
        Caisse loaded = caisseAdminService.findByReferenceEntity(caisse);
        if (loaded != null) {
            loaded.setSolde(loaded.getSolde().add(saved.getSolde()));
            caisseAdminService.update(loaded);
        }
    }

    private void updateBanque(Banque banque, Compte saved) {
        Banque loaded = banqueAdminService.findByReferenceEntity(banque);
        if (loaded != null) {
            loaded.setSolde(loaded.getSolde().add(saved.getSolde()));
            banqueAdminService.update(loaded);
        }
    }

    public Compte findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Compte findOrSave(Compte t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Compte result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Compte> findAll() {
        List<Compte> result = new ArrayList<>();
        result.addAll(dao.findComptesByBanqueNotNull());
        result.addAll(dao.findComptesByCaisseNotNull());
        result.addAll(dao.findComptsByCompteInstantaneeNotNull());
        return result;
    }

    public List<Compte> findByCriteria(CompteCriteria criteria) {
        List<Compte> content = null;
        if (criteria != null) {
            CompteSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CompteSpecification constructSpecification(CompteCriteria criteria) {
        CompteSpecification mySpecification =  (CompteSpecification) RefelexivityUtil.constructObjectUsingOneParam(CompteSpecification.class, criteria);
        return mySpecification;
    }

    public List<Compte> findPaginatedByCriteria(CompteCriteria criteria, int page, int pageSize, String order, String sortField) {
        CompteSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CompteCriteria criteria) {
        CompteSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Compte> findByBanqueId(Long id){
        return dao.findByBanqueId(id);
    }
    public List<Compte> findByCompteChargeId(Long id){
        return dao.findByCompteChargeId(id);
    }
    public int deleteByBanqueId(Long id){
        return dao.deleteByBanqueId(id);
    }
    public long countByBanqueCode(String code){
        return dao.countByBanqueCode(code);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            deleteAssociatedLists(id);
            dao.deleteById(id);
        }
        return condition;
    }

    public void deleteAssociatedLists(Long id) {
        // Charge le Compte avec ses transactions (destination et source)
        Compte compte = findById(id);

        // 1. Réinitialiser les comptes sources des transactions où ce Compte est la destination
        for (Transaction transaction : compte.getTransactionsDestination()) {
            Compte source = transaction.getCompteSource();
            BigDecimal montant = transaction.getMontant();

            // Réajuster le solde du compte source
            source.subtractDebit(montant); // Annule le débit initial
            source.recalcSolde();
            dao.save(source); // Sauvegarde les modifications
        }

        // 2. Réinitialiser les comptes destinations des transactions où ce Compte est la source
        for (Transaction transaction : compte.getTransactionsSource()) {
            Compte destination = transaction.getCompteDestination();
            BigDecimal montant = transaction.getMontant();

            // Réajuster le solde du compte destination
            destination.subtractCredit(montant); // Annule le crédit initial
            destination.recalcSolde();
            dao.save(destination);
        }

        // 3. Supprimer les entités associées (Banque, Caisse, etc.)
        if (compte.getBanque() != null)
            banqueService.deleteById(compte.getBanque().getId());
        if (compte.getCaisse() != null)
            caisseAdminService.deleteById(compte.getCaisse().getId());
        if (compte.getCompteInstantanee() != null)
            compteInstantaneeAdminService.deleteById(compte.getCompteInstantanee().getId());
        if (compte.getCompteCharge() != null)
            compteChargeAdminService.deleteById(compte.getCompteCharge().getId());
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Compte> delete(List<Compte> list) {
		List<Compte> result = new ArrayList();
        if (list != null) {
            for (Compte t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Compte create(Compte t) {
        Compte loaded = findByReferenceEntity(t);
        Compte saved;
        CompteAdmin byId = compteAdminAdminService.findAdmin();

        // Set the CompteAdmin reference
        t.setCompteAdmin(byId);
        if (loaded == null) {
            // New account
            saved = dao.save(t);
            if(!saved.getCode().contains("CHARGE") || saved.getCompteInstantanee()!=null){
                byId.getComptes().add(saved);
            }

            // Process any transactions
            if (t.getTransactions() != null) {
                t.getTransactions().forEach(element -> {
                    transactionService.create(element);
                });
            }
        } else {
            // Existing account update
            t.setId(loaded.getId());
            saved = dao.save(t);

            // Check if it's already in the list before adding
            if (!byId.getComptes().contains(saved)) {
                byId.getComptes().add(saved);
            }
        }

        // Force update of CompteAdmin solde
        if(saved.getCompteInstantanee()==null){
            byId.updateSolde();  // You need to make this method public
        }

        // Update the CompteAdmin
        CompteAdmin update = compteAdminAdminService.update(byId);
        System.out.println("solde: " + update.getSolde());

        return saved;
    }
    public Compte findWithAssociatedLists(Long id){
        Compte result = dao.findById(id).orElse(null);
        if(result!=null && result.getId() != null) {
        }
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Compte> update(List<Compte> ts, boolean createIfNotExist) {
        List<Compte> result = new ArrayList<>();
        if (ts != null) {
            for (Compte t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Compte loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Compte t, Compte loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }

    public void updateWithAssociatedLists(Compte compte){
    if(compte !=null && compte.getId() != null){
      /*  List<List<Transaction>> resultTransactions= transactionService.getToBeSavedAndToBeDeleted(transactionService.findByCompteId(compte.getId()),compte.getTransactions());
            transactionService.delete(resultTransactions.get(1));
        emptyIfNull(resultTransactions.get(0)).forEach(e -> e.setCompte(compte));
        transactionService.update(resultTransactions.get(0),true);
        }*/
    }
    }








    public Compte findByReferenceEntity(Compte t) {
        return t == null ? null : findByCode(t.getCode());
    }

    @Override
    public Compte findByCode(String code) {
        return dao.findByCode(code);
    }

    @Override
    public Compte findCharge() {
        return dao.findByCode("CHARGE");
    }

    @Override
    public Compte findCaisse(Caisse caisse) {
        return dao.findByCaisse(caisse);
    }

    @Override
    public List<Compte> findByCompteInstantaneNotNull() {
        return dao.findComptsByCompteInstantaneeNotNull();
    }

    @Override
    public List<Compte> findComptsByBanqueNotNull() {
        return dao.findComptesByBanqueNotNull();
    }

    @Override
    public Compte findCharge(CompteCharge compteCharge) {
        return dao.findByCompteCharge(compteCharge);
    }

    @Override
    public List<Compte> findComptsByCompteChargeNotNull() {
        return dao.findComptesByCompteChargeNotNull();
    }

    @Override
    public List<Compte> findComptsByCaisseNotNull() {
        return dao.findComptesByCaisseNotNull();
    }

    @Override
    public Compte findBanque(Banque banque) {
        return dao.findByBanque(banque);
    }

    public void findOrSaveAssociatedObject(Compte t){
        if( t != null) {
            t.setBanque(banqueService.findOrSave(t.getBanque()));
        }
    }



    public List<Compte> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<Compte>> getToBeSavedAndToBeDeleted(List<Compte> oldList, List<Compte> newList) {
        List<List<Compte>> result = new ArrayList<>();
        List<Compte> resultDelete = new ArrayList<>();
        List<Compte> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<Compte> oldList, List<Compte> newList, List<Compte> resultUpdateOrSave, List<Compte> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Compte myOld = oldList.get(i);
                Compte t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Compte myNew = newList.get(i);
                Compte t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }







    @Autowired
    private TransactionAdminService transactionService ;
    @Autowired
    private BanqueAdminService banqueService ;

    public CompteAdminServiceImpl(CompteDao dao) {
        this.dao = dao;
    }

    private CompteDao dao;
}
