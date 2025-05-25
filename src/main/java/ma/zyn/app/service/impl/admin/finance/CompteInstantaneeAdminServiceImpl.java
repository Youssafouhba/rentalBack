package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locataire.Reglement;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.criteria.core.finance.CompteInstantaneeCriteria;
import ma.zyn.app.dao.facade.core.finance.CompteInstantaneeDao;
import ma.zyn.app.dao.specification.core.finance.CompteInstantaneeSpecification;
import ma.zyn.app.service.facade.admin.finance.*;
import ma.zyn.app.service.facade.admin.locataire.LocataireAdminService;
import ma.zyn.app.service.facade.admin.locataire.TransactionAdminService;
import ma.zyn.app.service.facade.admin.locataire.TypeTransactiontAdminService;
import ma.zyn.app.service.impl.admin.locataire.TransactionAdminServiceImpl;
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

import static ma.zyn.app.zynerator.util.ListUtil.isEmpty;
import static ma.zyn.app.zynerator.util.ListUtil.isNotEmpty;

@Service
public class CompteInstantaneeAdminServiceImpl implements CompteInstantaneeAdminService {
    @Autowired
    private TransactionAdminService transactionAdminService;
    @Autowired private TypeTransactiontAdminService typeTransactiontAdminService;
    @Autowired
    private TypePaiementAdminService typePaiementAdminService;
    @Autowired
    private CompteLocataireAdminService compteLocataireAdminService;
    @Autowired
    private CompteAdminService compteAdminService;
    @Autowired
    private ModePaiementAdminService modePaiementAdminService;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public CompteInstantanee update(CompteInstantanee t) {
        CompteInstantanee loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{CompteInstantanee.class.getSimpleName(), t.getId().toString()});
        } else {
            updateWithAssociatedLists(t);
            dao.save(t);
            return loadedItem;
        }
    }

    public CompteInstantanee findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public CompteInstantanee findOrSave(CompteInstantanee t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            CompteInstantanee result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<CompteInstantanee> findAll() {
        return dao.findAll();
    }

    public List<CompteInstantanee> findByCriteria(CompteInstantaneeCriteria criteria) {
        List<CompteInstantanee> content = null;
        if (criteria != null) {
            CompteInstantaneeSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CompteInstantaneeSpecification constructSpecification(CompteInstantaneeCriteria criteria) {
        CompteInstantaneeSpecification mySpecification =  (CompteInstantaneeSpecification) RefelexivityUtil.constructObjectUsingOneParam(CompteInstantaneeSpecification.class, criteria);
        return mySpecification;
    }

    public List<CompteInstantanee> findPaginatedByCriteria(CompteInstantaneeCriteria criteria, int page, int pageSize, String order, String sortField) {
        CompteInstantaneeSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CompteInstantaneeCriteria criteria) {
        CompteInstantaneeSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public CompteInstantanee findByLocataireId(Long id){
        return dao.findByLocataireId(id);
    }
    public int deleteByLocataireId(Long id){
        return dao.deleteByLocataireId(id);
    }
    public long countByLocataireCode(String code){
        return dao.countByLocataireCode(code);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CompteInstantanee> delete(List<CompteInstantanee> list) {
        List<CompteInstantanee> result = new ArrayList();
        if (list != null) {
            for (CompteInstantanee t : list) {
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
    public CompteInstantanee create(CompteInstantanee t) {
        CompteInstantanee loaded = findByReferenceEntity(t);
        CompteInstantanee saved;
        if (loaded == null) {
            saved = dao.save(t);
            if (t.getTransactions() != null) {
                t.getTransactions().forEach(element-> {
                    transactionService.create(element);
                });
            }
            Compte compte = createCompte(saved);
            //createTransaction(compte,saved);
        }else {
            saved = null;
        }
        return saved;
    }


    public void createTransaction(Compte compte, CompteInstantanee saved) {
        Transaction transaction = new Transaction();
        transaction.setDate(saved.getDateCreation());
        transaction.setMontant(saved.getSolde());
        transaction.setDescription(saved.getDescription());
        // Récupération ou sauvegarde des objets
        if (saved.getLocataire() != null ) {
            transaction.setTypeTransaction(typeTransactiontAdminService.findReglement());
            transaction.setTypePaiement(typePaiementAdminService.findCredit());

        }else {
            transaction.setTypeTransaction(typeTransactiontAdminService.findAvoir());
            transaction.setTypePaiement(typePaiementAdminService.findDebit());
        }

        transaction.setModePaiement(modePaiementAdminService.findVirement());
        CompteLocataire byLocataireId = compteLocataireAdminService.findByLocataireId(saved.getLocataire().getId());
        transaction.setCompteLocataire(byLocataireId);
        transaction.setCompteDestination(compte);
        // Création de la transaction
        transactionAdminService.create(transaction);
    }





    public Compte createCompte(CompteInstantanee t) {
        Compte compte = new Compte();
        compte.setCompteInstantanee(t);
        compte.setCode(t.getNom());
        compte.setDateCreation(t.getDateCreation());
        compte.initialize(t.getSolde());
        return compteAdminService.create(compte);
    }

    public CompteInstantanee findWithAssociatedLists(Long id){
        CompteInstantanee result = dao.findById(id).orElse(null);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CompteInstantanee> update(List<CompteInstantanee> ts, boolean createIfNotExist) {
        List<CompteInstantanee> result = new ArrayList<>();
        if (ts != null) {
            for (CompteInstantanee t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    CompteInstantanee loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, CompteInstantanee t, CompteInstantanee loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }

    public void updateWithAssociatedLists(CompteInstantanee CompteInstantanee){
    }








    public CompteInstantanee findByReferenceEntity(CompteInstantanee t) {
        return t == null ? null : findByCode(t.getCode());
    }

    @Override
    public CompteInstantanee findByCode(String code) {
        return dao.findByCode(code);
    }

    public void findOrSaveAssociatedObject(CompteInstantanee t){
        if( t != null) {
            t.setLocataire(locataireService.findOrSave(t.getLocataire()));
        }
    }



    public List<CompteInstantanee> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<CompteInstantanee>> getToBeSavedAndToBeDeleted(List<CompteInstantanee> oldList, List<CompteInstantanee> newList) {
        List<List<CompteInstantanee>> result = new ArrayList<>();
        List<CompteInstantanee> resultDelete = new ArrayList<>();
        List<CompteInstantanee> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<CompteInstantanee> oldList, List<CompteInstantanee> newList, List<CompteInstantanee> resultUpdateOrSave, List<CompteInstantanee> resultDelete) {
        for (int i = 0; i < oldList.size(); i++) {
            CompteInstantanee myOld = oldList.get(i);
            CompteInstantanee t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
            if (t != null) {
                resultUpdateOrSave.add(t); // update
            } else {
                resultDelete.add(myOld);
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            CompteInstantanee myNew = newList.get(i);
            CompteInstantanee t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    public CompteInstantanee findByLocataireAndLocation(Locataire locataire, Location location) {
        return dao.findByLocataireAndLocation(locataire, location);
    }


    @Autowired
    private TransactionAdminService transactionService ;
    @Autowired
    private LocataireAdminService locataireService ;

    public CompteInstantaneeAdminServiceImpl(CompteInstantaneeDao dao) {
        this.dao = dao;
    }

    private CompteInstantaneeDao dao;
}
