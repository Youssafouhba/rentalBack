package ma.zyn.app.service.facade.admin.locaux;


import ma.zyn.app.bean.core.locaux.TypeLocal;
import ma.zyn.app.dao.criteria.core.locaux.TypeLocalCriteria;

import java.util.List;


public interface TypeLocalAdminService {







	TypeLocal create(TypeLocal t);

    TypeLocal update(TypeLocal t);

    List<TypeLocal> update(List<TypeLocal> ts,boolean createIfNotExist);

    TypeLocal findById(Long id);

    TypeLocal findOrSave(TypeLocal t);

    TypeLocal findByReferenceEntity(TypeLocal t);

    TypeLocal findWithAssociatedLists(Long id);

    List<TypeLocal> findAllOptimized();

    List<TypeLocal> findAll();

    List<TypeLocal> findByCriteria(TypeLocalCriteria criteria);

    List<TypeLocal> findPaginatedByCriteria(TypeLocalCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(TypeLocalCriteria criteria);

    List<TypeLocal> delete(List<TypeLocal> ts);

    boolean deleteById(Long id);

    List<List<TypeLocal>> getToBeSavedAndToBeDeleted(List<TypeLocal> oldList, List<TypeLocal> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
