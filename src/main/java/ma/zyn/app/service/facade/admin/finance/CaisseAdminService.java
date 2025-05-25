package ma.zyn.app.service.facade.admin.finance;

import java.util.List;
import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.dao.criteria.core.finance.CaisseCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface CaisseAdminService {







	Caisse create(Caisse t);

    Caisse update(Caisse t);

    List<Caisse> update(List<Caisse> ts,boolean createIfNotExist);

    Caisse findById(Long id);

    Caisse findOrSave(Caisse t);

    Caisse findByReferenceEntity(Caisse t);

    Caisse findWithAssociatedLists(Long id);

    List<Caisse> findAllOptimized();

    List<Caisse> findAll();

    List<Caisse> findByCriteria(CaisseCriteria criteria);

    List<Caisse> findPaginatedByCriteria(CaisseCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CaisseCriteria criteria);

    List<Caisse> delete(List<Caisse> ts);

    boolean deleteById(Long id);

    List<List<Caisse>> getToBeSavedAndToBeDeleted(List<Caisse> oldList, List<Caisse> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
