package ma.zyn.app.service.facade.admin.locataire;

import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.criteria.core.locataire.TransactionCriteria;

import java.util.List;


public interface TransactionAdminService {



    List<Transaction> findByTypeTransactionCode(String code);
    List<Transaction> findByTypeTransactionId(Long id);
    int deleteByTypeTransactionId(Long id);
    int deleteByTypeTransactionCode(String code);
    long countByTypeTransactionCode(String code);
    List<Transaction> findByModePaiementCode(String code);
    List<Transaction> findByModePaiementId(Long id);
    int deleteByModePaiementId(Long id);

    Transaction findByCode(String code);

    int deleteByModePaiementCode(String code);
    long countByModePaiementCode(String code);
    List<Transaction> findByTypePaiementCode(String code);
    List<Transaction> findByTypePaiementId(Long id);
    int deleteByTypePaiementId(Long id);
    int deleteByTypePaiementCode(String code);
    long countByTypePaiementCode(String code);
    List<Transaction> findByCompteSourceId(Long id);
    int deleteByCompteSourceId(Long id);
    long countByCompteSourceId(Long id);
    List<Transaction> findByCompteDestinationId(Long id);
    int deleteByCompteDestinationId(Long id);
    long countByCompteDestinationId(Long id);




	Transaction create(Transaction t);

    Transaction update(Transaction t);

    List<Transaction> update(List<Transaction> ts,boolean createIfNotExist);

    Transaction findById(Long id);

    Transaction findOrSave(Transaction t);

    Transaction findByReferenceEntity(Transaction t);

    Transaction findWithAssociatedLists(Long id);

    List<Transaction> findAllOptimized();

    List<Transaction> findAll();

    List<Transaction> findByCriteria(TransactionCriteria criteria);

    List<Transaction> findPaginatedByCriteria(TransactionCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(TransactionCriteria criteria);

    List<Transaction> delete(List<Transaction> ts);

    boolean deleteById(Long id);

    List<List<Transaction>> getToBeSavedAndToBeDeleted(List<Transaction> oldList, List<Transaction> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
