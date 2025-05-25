package ma.zyn.app.service.facade.admin.locataire;

import java.util.List;
import ma.zyn.app.bean.core.locataire.Reglement;
import ma.zyn.app.dao.criteria.core.locataire.ReglementCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface ReglementAdminService {



    List<Reglement> findByLocationId(Long id);
    int deleteByLocationId(Long id);
    long countByLocationCode(String code);




    Reglement create(Reglement t);

    Reglement update(Reglement t);

    List<Reglement> update(List<Reglement> ts,boolean createIfNotExist);

    Reglement findById(Long id);

    Reglement findOrSave(Reglement t);

    Reglement findByReferenceEntity(Reglement t);

    Reglement findWithAssociatedLists(Long id);

    List<Reglement> findAllOptimized();

    List<Reglement> findAll();

    List<Reglement> findByCriteria(ReglementCriteria criteria);

    List<Reglement> findPaginatedByCriteria(ReglementCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ReglementCriteria criteria);

    List<Reglement> delete(List<Reglement> ts);

    boolean deleteById(Long id);

    List<List<Reglement>> getToBeSavedAndToBeDeleted(List<Reglement> oldList, List<Reglement> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
