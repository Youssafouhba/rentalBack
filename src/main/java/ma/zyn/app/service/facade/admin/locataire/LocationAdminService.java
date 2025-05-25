package ma.zyn.app.service.facade.admin.locataire;

import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.dao.criteria.core.locataire.LocationCriteria;

import java.util.List;


public interface LocationAdminService {



    List<Location> findByLocataireId(Long id);
    int deleteByLocataireId(Long id);
    long countByLocataireCode(String code);
    List<Location> findByLocalId(Long id);
    int deleteByLocalId(Long id);
    long countByLocalCode(String code);




	Location create(Location t);

    Location update(Location t);

    List<Location> update(List<Location> ts,boolean createIfNotExist);

    Location findById(Long id);

    Location findOrSave(Location t);

    Location findByReferenceEntity(Location t);

    Location findWithAssociatedLists(Long id);

    List<Location> findAllOptimized();

    List<Location> findAll();

    List<Location> findByCriteria(LocationCriteria criteria);

    List<Location> findPaginatedByCriteria(LocationCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(LocationCriteria criteria);

    List<Location> delete(List<Location> ts);

    boolean deleteById(Long id);

    List<List<Location>> getToBeSavedAndToBeDeleted(List<Location> oldList, List<Location> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
