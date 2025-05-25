package ma.zyn.app.service.facade.admin.finance;

import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.dao.criteria.core.finance.CompteLocataireCriteria;

import java.util.List;


public interface CompteLocataireAdminService {



    CompteLocataire findByLocataireId(Long id);
    int deleteByLocataireId(Long id);
    long countByLocataireCode(String code);




    CompteLocataire create(CompteLocataire t);

    CompteLocataire update(CompteLocataire t);

    List<CompteLocataire> update(List<CompteLocataire> ts,boolean createIfNotExist);

    CompteLocataire findById(Long id);

    CompteLocataire findOrSave(CompteLocataire t);

    CompteLocataire findByReferenceEntity(CompteLocataire t);

    CompteLocataire findWithAssociatedLists(Long id);

    List<CompteLocataire> findAllOptimized();

    List<CompteLocataire> findAll();

    List<CompteLocataire> findByCriteria(CompteLocataireCriteria criteria);

    List<CompteLocataire> findPaginatedByCriteria(CompteLocataireCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CompteLocataireCriteria criteria);

    List<CompteLocataire> delete(List<CompteLocataire> ts);

    boolean deleteById(Long id);

    List<List<CompteLocataire>> getToBeSavedAndToBeDeleted(List<CompteLocataire> oldList, List<CompteLocataire> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
