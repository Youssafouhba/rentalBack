package ma.zyn.app.service.facade.admin.finance;

import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.dao.criteria.core.finance.CompteInstantaneeCriteria;

import java.util.List;


public interface CompteInstantaneeAdminService {



    CompteInstantanee findByLocataireId(Long id);
    int deleteByLocataireId(Long id);
    long countByLocataireCode(String code);




    CompteInstantanee create(CompteInstantanee t);

    CompteInstantanee update(CompteInstantanee t);

    List<CompteInstantanee> update(List<CompteInstantanee> ts,boolean createIfNotExist);

    CompteInstantanee findById(Long id);

    CompteInstantanee findOrSave(CompteInstantanee t);

    CompteInstantanee findByReferenceEntity(CompteInstantanee t);

    CompteInstantanee findWithAssociatedLists(Long id);

    CompteInstantanee findByCode(String code);

    List<CompteInstantanee> findAllOptimized();

    List<CompteInstantanee> findAll();

    List<CompteInstantanee> findByCriteria(CompteInstantaneeCriteria criteria);

    List<CompteInstantanee> findPaginatedByCriteria(CompteInstantaneeCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CompteInstantaneeCriteria criteria);

    List<CompteInstantanee> delete(List<CompteInstantanee> ts);

    boolean deleteById(Long id);

    List<List<CompteInstantanee>> getToBeSavedAndToBeDeleted(List<CompteInstantanee> oldList, List<CompteInstantanee> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    CompteInstantanee findByLocataireAndLocation(Locataire locataire, Location location);
}
