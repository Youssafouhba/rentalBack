package ma.zyn.app.service.facade.admin.finance;

import ma.zyn.app.bean.core.finance.TypeCharge;
import ma.zyn.app.dao.criteria.core.finance.TypeChargeCriteria;

import java.util.List;


public interface TypeChargeAdminService {







	TypeCharge create(TypeCharge t);

    TypeCharge update(TypeCharge t);

    List<TypeCharge> update(List<TypeCharge> ts,boolean createIfNotExist);

    TypeCharge findById(Long id);

    TypeCharge findOrSave(TypeCharge t);

    TypeCharge findByReferenceEntity(TypeCharge t);

    TypeCharge findWithAssociatedLists(Long id);

    List<TypeCharge> findAllOptimized();

    List<TypeCharge> findAll();

    List<TypeCharge> findByCriteria(TypeChargeCriteria criteria);

    List<TypeCharge> findPaginatedByCriteria(TypeChargeCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(TypeChargeCriteria criteria);

    List<TypeCharge> delete(List<TypeCharge> ts);

    boolean deleteById(Long id);

    TypeCharge findByCode(String code);

    List<List<TypeCharge>> getToBeSavedAndToBeDeleted(List<TypeCharge> oldList, List<TypeCharge> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
