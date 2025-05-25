package ma.zyn.app.service.facade.admin.locataire;

import java.util.List;
import ma.zyn.app.bean.core.locataire.TypeLocataire;
import ma.zyn.app.dao.criteria.core.locataire.TypeLocataireCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface TypeLocataireAdminService {







	TypeLocataire create(TypeLocataire t);

    TypeLocataire update(TypeLocataire t);

    List<TypeLocataire> update(List<TypeLocataire> ts,boolean createIfNotExist);

    TypeLocataire findById(Long id);

    TypeLocataire findOrSave(TypeLocataire t);

    TypeLocataire findByReferenceEntity(TypeLocataire t);

    TypeLocataire findWithAssociatedLists(Long id);

    List<TypeLocataire> findAllOptimized();

    List<TypeLocataire> findAll();

    List<TypeLocataire> findByCriteria(TypeLocataireCriteria criteria);

    List<TypeLocataire> findPaginatedByCriteria(TypeLocataireCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(TypeLocataireCriteria criteria);

    List<TypeLocataire> delete(List<TypeLocataire> ts);

    boolean deleteById(Long id);

    List<List<TypeLocataire>> getToBeSavedAndToBeDeleted(List<TypeLocataire> oldList, List<TypeLocataire> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
