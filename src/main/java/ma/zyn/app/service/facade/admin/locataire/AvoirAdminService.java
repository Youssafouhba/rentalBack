package ma.zyn.app.service.facade.admin.locataire;

import ma.zyn.app.bean.core.locataire.Avoir;
import ma.zyn.app.dao.criteria.core.locataire.AvoirCriteria;

import java.util.List;


public interface AvoirAdminService {



    List<Avoir> findByLocationId(Long id);
    int deleteByLocationId(Long id);
    long countByLocationCode(String code);




	Avoir create(Avoir t);

    Avoir update(Avoir t);

    List<Avoir> update(List<Avoir> ts,boolean createIfNotExist);

    Avoir findById(Long id);

    Avoir findOrSave(Avoir t);

    Avoir findByReferenceEntity(Avoir t);

    Avoir findWithAssociatedLists(Long id);

    Avoir findByCode(String code);

    List<Avoir> findAllOptimized();

    List<Avoir> findAll();

    List<Avoir> findByCriteria(AvoirCriteria criteria);

    List<Avoir> findPaginatedByCriteria(AvoirCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(AvoirCriteria criteria);

    List<Avoir> delete(List<Avoir> ts);

    boolean deleteById(Long id);

    List<List<Avoir>> getToBeSavedAndToBeDeleted(List<Avoir> oldList, List<Avoir> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    void deleteByLocataireId(Long id);

    List<Avoir> findByLocataireId(Long id);
}
