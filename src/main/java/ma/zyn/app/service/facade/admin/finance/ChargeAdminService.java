package ma.zyn.app.service.facade.admin.finance;

import java.util.List;
import ma.zyn.app.bean.core.finance.Charge;
import ma.zyn.app.dao.criteria.core.finance.ChargeCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface ChargeAdminService {


    List<Charge> findByTypeChargeCode(String code);
    List<Charge> findByTypeChargeId(Long id);
    int deleteByTypeChargeId(Long id);
    int deleteByTypeChargeCode(String code);
    long countByTypeChargeCode(String code);
    List<Charge> findByLocalId(Long id);
    int deleteByLocalId(Long id);
    long countByLocalCode(String code);




    Charge create(Charge t);

    Charge update(Charge t);

    List<Charge> update(List<Charge> ts,boolean createIfNotExist);

    Charge findById(Long id);

    Charge findOrSave(Charge t);

    Charge findByReferenceEntity(Charge t);

    Charge findWithAssociatedLists(Long id);

    List<Charge> findAllOptimized();

    List<Charge> findAll();

    List<Charge> findByCriteria(ChargeCriteria criteria);

    List<Charge> findPaginatedByCriteria(ChargeCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ChargeCriteria criteria);

    List<Charge> delete(List<Charge> ts);

    boolean deleteById(Long id);

    List<List<Charge>> getToBeSavedAndToBeDeleted(List<Charge> oldList, List<Charge> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    void deleteByCompteChargeId(Long id);

    List<Charge> findByCompteChargeId(Long id);
}

