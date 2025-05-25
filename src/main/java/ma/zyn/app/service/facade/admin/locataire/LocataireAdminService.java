package ma.zyn.app.service.facade.admin.locataire;

import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.dao.criteria.core.locataire.LocataireCriteria;

import java.util.List;


public interface LocataireAdminService {



    List<Locataire> findByTypeLocataireCode(String code);
    List<Locataire> findByTypeLocataireId(Long id);
    int deleteByTypeLocataireId(Long id);
    int deleteByTypeLocataireCode(String code);
    long countByTypeLocataireCode(String code);
    List<Locataire> findByCompteLocataireId(Long id);
    int deleteByCompteLocataireId(Long id);
    long countByCompteLocataireId(Long id);




    Locataire create(Locataire t);

    Locataire update(Locataire t);

    List<Locataire> update(List<Locataire> ts,boolean createIfNotExist);

    Locataire findById(Long id);

    Locataire findOrSave(Locataire t);

    Locataire findByReferenceEntity(Locataire t);

    Locataire findWithAssociatedLists(Long id);

    List<Locataire> findAllOptimized();

    List<Locataire> findAll();

    List<Locataire> findByCriteria(LocataireCriteria criteria);

    List<Locataire> findPaginatedByCriteria(LocataireCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(LocataireCriteria criteria);

    List<Locataire> delete(List<Locataire> ts);

    boolean deleteById(Long id);

    List<List<Locataire>> getToBeSavedAndToBeDeleted(List<Locataire> oldList, List<Locataire> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
