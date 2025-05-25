package ma.zyn.app.service.facade.admin.locataire;

import java.util.List;
import ma.zyn.app.bean.core.locataire.StatutLocal;
import ma.zyn.app.dao.criteria.core.locataire.StatutLocalCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface StatutLocalAdminService {







	StatutLocal create(StatutLocal t);

    StatutLocal update(StatutLocal t);

    List<StatutLocal> update(List<StatutLocal> ts,boolean createIfNotExist);

    StatutLocal findById(Long id);

    StatutLocal findOrSave(StatutLocal t);

    StatutLocal findByReferenceEntity(StatutLocal t);

    StatutLocal findWithAssociatedLists(Long id);

    List<StatutLocal> findAllOptimized();

    List<StatutLocal> findAll();

    List<StatutLocal> findByCriteria(StatutLocalCriteria criteria);

    List<StatutLocal> findPaginatedByCriteria(StatutLocalCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(StatutLocalCriteria criteria);

    List<StatutLocal> delete(List<StatutLocal> ts);

    boolean deleteById(Long id);

    List<List<StatutLocal>> getToBeSavedAndToBeDeleted(List<StatutLocal> oldList, List<StatutLocal> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    StatutLocal findByCode(String code);

    StatutLocal findAllowed();
}
