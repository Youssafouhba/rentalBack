package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.service.facade.admin.finance.CompteAdminService;
import ma.zyn.app.service.facade.admin.finance.TypeChargeAdminService;
import ma.zyn.app.service.facade.admin.finance.TypePaiementAdminService;
import ma.zyn.app.service.facade.admin.locataire.TransactionAdminService;
import ma.zyn.app.service.facade.admin.locataire.TypeTransactiontAdminService;
import ma.zyn.app.service.facade.admin.locaux.LocalAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.finance.Charge;
import ma.zyn.app.dao.criteria.core.finance.ChargeCriteria;
import ma.zyn.app.dao.facade.core.finance.ChargeDao;
import ma.zyn.app.dao.specification.core.finance.ChargeSpecification;
import ma.zyn.app.service.facade.admin.finance.ChargeAdminService;

import static ma.zyn.app.zynerator.util.ListUtil.*;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.zynerator.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChargeAdminServiceImpl implements ChargeAdminService {
    @Autowired
    private CompteChargeAdminServiceImpl compteChargeAdminService;
    @Autowired
    private TypeTransactiontAdminService typeTransactiontAdminService;
    @Autowired
    private TypePaiementAdminService typePaiementAdminService;
    @Autowired
    private TransactionAdminService transactionAdminService;
    @Autowired
    private CompteAdminService compteAdminService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Charge update(Charge t) {
        Charge loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Charge.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Charge findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Charge findOrSave(Charge t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Charge result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Charge> findAll() {
        return dao.findAll();
    }

    public List<Charge> findByCriteria(ChargeCriteria criteria) {
        List<Charge> content = null;
        if (criteria != null) {
            ChargeSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ChargeSpecification constructSpecification(ChargeCriteria criteria) {
        ChargeSpecification mySpecification =  (ChargeSpecification) RefelexivityUtil.constructObjectUsingOneParam(ChargeSpecification.class, criteria);
        return mySpecification;
    }

    public List<Charge> findPaginatedByCriteria(ChargeCriteria criteria, int page, int pageSize, String order, String sortField) {
        ChargeSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ChargeCriteria criteria) {
        ChargeSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Charge> findByTypeChargeCode(String code){
        return dao.findByTypeChargeCode(code);
    }
    public List<Charge> findByTypeChargeId(Long id){
        return dao.findByTypeChargeId(id);
    }
    public int deleteByTypeChargeCode(String code){
        return dao.deleteByTypeChargeCode(code);
    }
    public int deleteByTypeChargeId(Long id){
        return dao.deleteByTypeChargeId(id);
    }
    public long countByTypeChargeCode(String code){
        return dao.countByTypeChargeCode(code);
    }
    public List<Charge> findByLocalId(Long id){
        return dao.findByLocalId(id);
    }
    public int deleteByLocalId(Long id){
        return dao.deleteByLocalId(id);
    }
    public long countByLocalCode(String code){
        return dao.countByLocalCode(code);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public boolean deleteById(Long chargeId) {
        if (chargeId == null) return false;

        // 1. Trouver la Charge et son CompteCharge associé
        Charge charge = findById(chargeId);
        CompteCharge compteCharge = charge.getCompteCharge();

        // 2. Trouver le Compte lié au CompteCharge
        Compte compteChargeCompte = compteAdminService.findCharge(compteCharge);
        compteChargeCompte.subtractCredit(charge.getMontant()); // Annule le crédit initial
        compteAdminService.update(compteChargeCompte); // Sauvegarde les modifications


        // 1. Réinitialiser les comptes sources des transactions où ce Compte est la destination
        for (Transaction transaction : compteChargeCompte.getTransactionsDestination()) {
            Compte source = transaction.getCompteSource();
            BigDecimal montant = transaction.getMontant();

            // Réajuster le solde du compte source
            source.subtractDebit(montant); // Annule le débit initial
            source.recalcSolde();
            transaction.setCompteSource(null);
            transaction.setCompteDestination(null);
            transactionAdminService.deleteById(transaction.getId());
            compteAdminService.update(source); // Sauvegarde les modifications
        }

        // 2. Réinitialiser les comptes destinations des transactions où ce Compte est la source
        for (Transaction transaction : compteChargeCompte.getTransactionsSource()) {
            Compte destination = transaction.getCompteDestination();
            BigDecimal montant = transaction.getMontant();

            // Réajuster le solde du compte destination
            destination.subtractCredit(montant); // Annule le crédit initial
            destination.recalcSolde();
            transaction.setCompteSource(null);
            transaction.setCompteDestination(null);
            transactionAdminService.deleteById(transaction.getId());
            compteAdminService.update(destination);
        }
        // 5. Supprimer la Charge elle-même
        dao.deleteById(chargeId);

        return true;
    }

    public void deleteAssociatedLists(Long chargeId) {
        // 1. Trouver la Charge et son CompteCharge associé
        Charge charge = findById(chargeId);
        CompteCharge compteCharge = charge.getCompteCharge();

        if (compteCharge != null) {

            // 2. Trouver tous les Compte liés à ce CompteCharge
            List<Compte> comptesAssocies = compteAdminService.findByCompteChargeId(compteCharge.getId());

            // 3. Détacher le CompteCharge des Compte
            for (Compte compte : comptesAssocies) {
                compte.subtractDebit(charge.getMontant()); // Annule le crédit initial
                compteAdminService.update(compte); // Sauvegarder le changement
            }

            // 4. Supprimer les transactions liées au CompteCharge
            List<Transaction> transactions = transactionAdminService.findByCompteDestinationId(compteCharge.getId());
            transactionAdminService.delete(transactions);

        }

    }






    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Charge> delete(List<Charge> list) {
        List<Charge> result = new ArrayList();
        if (list != null) {
            for (Charge t : list) {
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
    public Charge create(Charge t) {
        t.setCode(RandomStringUtils.randomAlphanumeric(5));
        Charge loaded = findByReferenceEntity(t);
        Charge saved;
        if (loaded == null) {
            saved = dao.save(t);
            createTransaction(saved);
        }else {
            saved = null;
        }
        return saved;
    }

    public Charge findWithAssociatedLists(Long id){
        Charge result = dao.findById(id).orElse(null);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Charge> update(List<Charge> ts, boolean createIfNotExist) {
        List<Charge> result = new ArrayList<>();
        if (ts != null) {
            for (Charge t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Charge loadedItem = dao.findById(t.getId()).orElse(null);
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


    public void createTransaction(Charge charge) {
        // Validation du règlement
        if (charge == null || charge.getMontant() == null || charge.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("charge invalide.");
        }
        Transaction transaction = new Transaction();
        transaction.setDate(charge.getDate());
        transaction.setDescription(charge.getDescription());
        transaction.setMontant(charge.getMontant());
        // Récupération ou sauvegarde des objets
        transaction.setTypeTransaction(typeTransactiontAdminService.findCharge());
        transaction.setTypePaiement(typePaiementAdminService.findDebit());
        transaction.setModePaiement(charge.getModePaiement());
        transaction.setCompteSource(charge.getCompteSource());
        Compte comte2Charge = compteAdminService.findCharge(charge.getCompteCharge());
        transaction.setCompteDestination(comte2Charge);
        // Création de la transaction
        transactionAdminService.create(transaction);
    }



    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Charge t, Charge loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Charge findByReferenceEntity(Charge t){
        return t==null? null : dao.findByCode(t.getCode());
    }
    public void findOrSaveAssociatedObject(Charge t){
        if( t != null) {
            t.setTypeCharge(typeChargeService.findOrSave(t.getTypeCharge()));
            t.setLocal(localService.findOrSave(t.getLocal()));
        }
    }



    public List<Charge> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Charge>> getToBeSavedAndToBeDeleted(List<Charge> oldList, List<Charge> newList) {
        List<List<Charge>> result = new ArrayList<>();
        List<Charge> resultDelete = new ArrayList<>();
        List<Charge> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Charge> oldList, List<Charge> newList, List<Charge> resultUpdateOrSave, List<Charge> resultDelete) {
        for (int i = 0; i < oldList.size(); i++) {
            Charge myOld = oldList.get(i);
            Charge t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
            if (t != null) {
                resultUpdateOrSave.add(t); // update
            } else {
                resultDelete.add(myOld);
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            Charge myNew = newList.get(i);
            Charge t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
            if (t == null) {
                resultUpdateOrSave.add(myNew); // create
            }
        }
    }

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }

    @Override
    public void deleteByCompteChargeId(Long id) {
        dao.deleteByCompteChargeId(id);
    }

    @Override
    public List<Charge> findByCompteChargeId(Long id) {
        return dao.findByCompteChargeId(id);
    }


    @Autowired
    private LocalAdminService localService ;
    @Autowired
    private TypeChargeAdminService typeChargeService ;

    public ChargeAdminServiceImpl(ChargeDao dao) {
        this.dao = dao;
    }

    private ChargeDao dao;
}
