package ma.zyn.app.service.facade.admin.finance;

import ma.zyn.app.bean.core.finance.CompteAdmin;
import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.dao.criteria.core.finance.CompteAdminCriteria;

import java.util.List;


public interface CompteAdminAdminService {







	CompteAdmin create(CompteAdmin t);

    CompteAdmin update(CompteAdmin t);

    List<CompteAdmin> update(List<CompteAdmin> ts,boolean createIfNotExist);

    CompteAdmin findById(Long id);

    CompteAdmin findOrSave(CompteAdmin t);

    CompteAdmin findByReferenceEntity(CompteAdmin t);

    CompteAdmin findWithAssociatedLists(Long id);

    CompteAdmin findAdmin();

    List<CompteAdmin> findAllOptimized();

    List<CompteAdmin> findAll();

    List<CompteAdmin> findByCriteria(CompteAdminCriteria criteria);

    List<CompteAdmin> findPaginatedByCriteria(CompteAdminCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CompteAdminCriteria criteria);

    List<CompteAdmin> delete(List<CompteAdmin> ts);

    boolean deleteById(Long id);

    List<List<CompteAdmin>> getToBeSavedAndToBeDeleted(List<CompteAdmin> oldList, List<CompteAdmin> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
