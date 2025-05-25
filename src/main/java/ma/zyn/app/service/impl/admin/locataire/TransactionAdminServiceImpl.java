package ma.zyn.app.service.impl.admin.locataire;


import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.criteria.core.locataire.TransactionCriteria;
import ma.zyn.app.dao.facade.core.locataire.TransactionDao;
import ma.zyn.app.dao.specification.core.locataire.TransactionSpecification;
import ma.zyn.app.service.facade.admin.finance.CompteAdminService;
import ma.zyn.app.service.facade.admin.finance.CompteLocataireAdminService;
import ma.zyn.app.service.facade.admin.finance.ModePaiementAdminService;
import ma.zyn.app.service.facade.admin.finance.TypePaiementAdminService;
import ma.zyn.app.service.facade.admin.locataire.TransactionAdminService;
import ma.zyn.app.service.facade.admin.locataire.TypeTransactiontAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static ma.zyn.app.zynerator.util.ListUtil.isEmpty;
import static ma.zyn.app.zynerator.util.ListUtil.isNotEmpty;

@Service
public class TransactionAdminServiceImpl implements TransactionAdminService {

    @Autowired
    private CompteLocataireAdminService compteLocataireAdminService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Transaction update(Transaction t) {
        Transaction loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Transaction.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Transaction findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Transaction findOrSave(Transaction t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Transaction result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Transaction> findAll() {
        return dao.findAll();
    }

    public List<Transaction> findByCriteria(TransactionCriteria criteria) {
        List<Transaction> content = null;
        if (criteria != null) {
            TransactionSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private TransactionSpecification constructSpecification(TransactionCriteria criteria) {
        TransactionSpecification mySpecification =  (TransactionSpecification) RefelexivityUtil.constructObjectUsingOneParam(TransactionSpecification.class, criteria);
        return mySpecification;
    }

    public List<Transaction> findPaginatedByCriteria(TransactionCriteria criteria, int page, int pageSize, String order, String sortField) {
        TransactionSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(TransactionCriteria criteria) {
        TransactionSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Transaction> findByTypeTransactionCode(String code){
        return dao.findByTypeTransactionCode(code);
    }
    public List<Transaction> findByTypeTransactionId(Long id){
        return dao.findByTypeTransactionId(id);
    }
    public int deleteByTypeTransactionCode(String code){
        return dao.deleteByTypeTransactionCode(code);
    }
    public int deleteByTypeTransactionId(Long id){
        return dao.deleteByTypeTransactionId(id);
    }
    public long countByTypeTransactionCode(String code){
        return dao.countByTypeTransactionCode(code);
    }
    public List<Transaction> findByModePaiementCode(String code){
        return dao.findByModePaiementCode(code);
    }
    public List<Transaction> findByModePaiementId(Long id){
        return dao.findByModePaiementId(id);
    }
    @Override
    public Transaction findByCode(String code){
        return dao.findByCode(code);
    }
    public int deleteByModePaiementCode(String code){
        return dao.deleteByModePaiementCode(code);
    }
    public int deleteByModePaiementId(Long id){
        return dao.deleteByModePaiementId(id);
    }
    public long countByModePaiementCode(String code){
        return dao.countByModePaiementCode(code);
    }
    public List<Transaction> findByTypePaiementCode(String code){
        return dao.findByTypePaiementCode(code);
    }
    public List<Transaction> findByTypePaiementId(Long id){
        return dao.findByTypePaiementId(id);
    }
    public int deleteByTypePaiementCode(String code){
        return dao.deleteByTypePaiementCode(code);
    }
    public int deleteByTypePaiementId(Long id){
        return dao.deleteByTypePaiementId(id);
    }
    public long countByTypePaiementCode(String code){
        return dao.countByTypePaiementCode(code);
    }
    public List<Transaction> findByCompteSourceId(Long id){
        return dao.findByCompteSourceId(id);
    }
    public int deleteByCompteSourceId(Long id){
        return dao.deleteByCompteSourceId(id);
    }
    public long countByCompteSourceId(Long id){
        return dao.countByCompteSourceId(id);
    }
    public List<Transaction> findByCompteDestinationId(Long id){
        return dao.findByCompteDestinationId(id);
    }
    public int deleteByCompteDestinationId(Long id){
        return dao.deleteByCompteDestinationId(id);
    }
    public long countByCompteDestinationId(Long id){
        return dao.countByCompteDestinationId(id);
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
    public List<Transaction> delete(List<Transaction> list) {
        List<Transaction> result = new ArrayList();
        if (list != null) {
            for (Transaction t : list) {
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
    public Transaction create(Transaction t) {
        Transaction loaded = findByReferenceEntity(t);
        Transaction saved;
        if (loaded == null) {
            saved = dao.save(t);
            // Mise à jour du compte source
            if (t.getCompteSource() != null) {
                updateCompteSource(saved.getCompteSource(), saved);
            }

            // Mise à jour du compte destination
            if (t.getCompteDestination() != null) {
                updateCompteDestination(saved.getCompteDestination(), saved);
            }

            // Mise à jour du compte locataire
            if (t.getCompteLocataire() != null) {
                updateCompteLocataire(saved.getCompteLocataire(), saved);
            }
        }else {
            saved = null;
        }
        return saved;
    }


    private void updateCompteSource(Compte compteSource,Transaction transaction) {
        compteSource.addDebit(transaction.getMontant());
        compteService.update(compteSource); // Mise à jour dans la base de données
    }

    private void updateCompteDestination(Compte compteDestination, Transaction transaction) {
        compteDestination.addCredit(transaction.getMontant());
        compteService.update(compteDestination); // Mise à jour dans la base de données
    }



    private void updateCompteLocataire(CompteLocataire compteLocataire, Transaction transaction) {
        if ("reglement".equalsIgnoreCase(transaction.getTypeTransaction().getCode())) {
            compteLocataire.addDebit(transaction.getMontant());
        } else if ("avoir".equalsIgnoreCase(transaction.getTypeTransaction().getCode())) {
            compteLocataire.addCredit(transaction.getMontant()); // Ajouter au total des crédits
        } else if ("charge".equalsIgnoreCase(transaction.getTypeTransaction().getCode())) {
            compteLocataire.addCredit(transaction.getMontant()); // Ajouter au total des crédits
        }
        compteLocataireAdminService.update(compteLocataire); // Mise à jour dans la base de données
    }


    public Transaction findWithAssociatedLists(Long id){
        Transaction result = dao.findById(id).orElse(null);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Transaction> update(List<Transaction> ts, boolean createIfNotExist) {
        List<Transaction> result = new ArrayList<>();
        if (ts != null) {
            for (Transaction t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Transaction loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Transaction t, Transaction loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Transaction findByReferenceEntity(Transaction t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(Transaction t){
        if( t != null) {
            t.setTypeTransaction(typeTransactiontService.findOrSave(t.getTypeTransaction()));
            t.setModePaiement(modePaiementService.findOrSave(t.getModePaiement()));
            t.setTypePaiement(typePaiementService.findOrSave(t.getTypePaiement()));
            t.setCompteSource(compteService.findOrSave(t.getCompteSource()));
            t.setCompteDestination(compteService.findOrSave(t.getCompteDestination()));
        }
    }



    public List<Transaction> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<Transaction>> getToBeSavedAndToBeDeleted(List<Transaction> oldList, List<Transaction> newList) {
        List<List<Transaction>> result = new ArrayList<>();
        List<Transaction> resultDelete = new ArrayList<>();
        List<Transaction> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Transaction> oldList, List<Transaction> newList, List<Transaction> resultUpdateOrSave, List<Transaction> resultDelete) {
        for (int i = 0; i < oldList.size(); i++) {
            Transaction myOld = oldList.get(i);
            Transaction t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
            if (t != null) {
                resultUpdateOrSave.add(t); // update
            } else {
                resultDelete.add(myOld);
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            Transaction myNew = newList.get(i);
            Transaction t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private TypePaiementAdminService typePaiementService ;
    @Autowired
    private CompteAdminService compteService ;
    @Autowired
    private ModePaiementAdminService modePaiementService ;
    @Autowired
    private TypeTransactiontAdminService typeTransactiontService ;

    public TransactionAdminServiceImpl(TransactionDao dao) {
        this.dao = dao;
    }

    private TransactionDao dao;
}
