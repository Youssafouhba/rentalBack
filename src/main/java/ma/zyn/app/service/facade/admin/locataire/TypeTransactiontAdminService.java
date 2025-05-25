package ma.zyn.app.service.facade.admin.locataire;

import ma.zyn.app.bean.core.locataire.TypeTransactiont;
import ma.zyn.app.dao.criteria.core.locataire.TypeTransactiontCriteria;

import java.util.List;


public interface TypeTransactiontAdminService {







	TypeTransactiont create(TypeTransactiont t);

    TypeTransactiont update(TypeTransactiont t);

    List<TypeTransactiont> update(List<TypeTransactiont> ts,boolean createIfNotExist);

    TypeTransactiont findById(Long id);

    TypeTransactiont findOrSave(TypeTransactiont t);

    TypeTransactiont findByReferenceEntity(TypeTransactiont t);

    TypeTransactiont findWithAssociatedLists(Long id);

    TypeTransactiont findReglement();

    TypeTransactiont findCharge();

    TypeTransactiont findAvoir();

    List<TypeTransactiont> findAllOptimized();

    List<TypeTransactiont> findAll();

    List<TypeTransactiont> findByCriteria(TypeTransactiontCriteria criteria);

    List<TypeTransactiont> findPaginatedByCriteria(TypeTransactiontCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(TypeTransactiontCriteria criteria);

    List<TypeTransactiont> delete(List<TypeTransactiont> ts);

    boolean deleteById(Long id);

    List<List<TypeTransactiont>> getToBeSavedAndToBeDeleted(List<TypeTransactiont> oldList, List<TypeTransactiont> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
