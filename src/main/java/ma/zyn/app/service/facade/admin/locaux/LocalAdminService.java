package ma.zyn.app.service.facade.admin.locaux;


import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.dao.criteria.core.locaux.LocalCriteria;

import java.util.List;

public interface LocalAdminService {



    List<Local> findByStatutLocalCode(String code);
    List<Local> findByStatutLocalId(Long id);
    int deleteByStatutLocalId(Long id);
    int deleteByStatutLocalCode(String code);
    long countByStatutLocalCode(String code);
    List<Local> findByTypeLocalCode(String code);
    List<Local> findByTypeLocalId(Long id);
    int deleteByTypeLocalId(Long id);
    int deleteByTypeLocalCode(String code);
    long countByTypeLocalCode(String code);




	Local create(Local t);

    Local update(Local t);

    List<Local> update(List<Local> ts,boolean createIfNotExist);

    Local findById(Long id);

    Local findOrSave(Local t);

    Local findByReferenceEntity(Local t);

    Local findWithAssociatedLists(Long id);

    List<Local> findAllOptimized();

    List<Local> findAll();

    List<Local> findByCriteria(LocalCriteria criteria);

    List<Local> findPaginatedByCriteria(LocalCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(LocalCriteria criteria);

    List<Local> delete(List<Local> ts);

    boolean deleteById(Long id);

    List<List<Local>> getToBeSavedAndToBeDeleted(List<Local> oldList, List<Local> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
