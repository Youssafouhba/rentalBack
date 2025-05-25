package ma.zyn.app.service.facade.admin.finance;

import ma.zyn.app.bean.core.finance.ModePaiement;
import ma.zyn.app.dao.criteria.core.finance.ModePaiementCriteria;

import java.util.List;


public interface ModePaiementAdminService {







	ModePaiement create(ModePaiement t);

    ModePaiement update(ModePaiement t);

    List<ModePaiement> update(List<ModePaiement> ts,boolean createIfNotExist);

    ModePaiement findById(Long id);

    ModePaiement findOrSave(ModePaiement t);

    ModePaiement findByReferenceEntity(ModePaiement t);

    ModePaiement findWithAssociatedLists(Long id);

    List<ModePaiement> findAllOptimized();

    List<ModePaiement> findAll();

    List<ModePaiement> findByCriteria(ModePaiementCriteria criteria);

    List<ModePaiement> findPaginatedByCriteria(ModePaiementCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ModePaiementCriteria criteria);

    List<ModePaiement> delete(List<ModePaiement> ts);

    boolean deleteById(Long id);

    List<List<ModePaiement>> getToBeSavedAndToBeDeleted(List<ModePaiement> oldList, List<ModePaiement> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    ModePaiement findVirement();

    ModePaiement findByCode(String code);
}
