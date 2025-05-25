package ma.zyn.app.service.facade.admin.finance;

import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.dao.criteria.core.finance.CompteChargeCriteria;

import java.util.List;


public interface CompteChargeAdminService {







	CompteCharge create(CompteCharge t);

    CompteCharge update(CompteCharge t);

    List<CompteCharge> update(List<CompteCharge> ts,boolean createIfNotExist);

    CompteCharge findById(Long id);

    CompteCharge findOrSave(CompteCharge t);

    CompteCharge findByReferenceEntity(CompteCharge t);

    CompteCharge findWithAssociatedLists(Long id);

    List<CompteCharge> findAllOptimized();

    List<CompteCharge> findAll();

    List<CompteCharge> findByCriteria(CompteChargeCriteria criteria);

    List<CompteCharge> findPaginatedByCriteria(CompteChargeCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CompteChargeCriteria criteria);

    List<CompteCharge> delete(List<CompteCharge> ts);

    boolean deleteById(Long id);

    List<List<CompteCharge>> getToBeSavedAndToBeDeleted(List<CompteCharge> oldList, List<CompteCharge> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    CompteCharge findCharge();

    CompteCharge findByCode(String code);
}
