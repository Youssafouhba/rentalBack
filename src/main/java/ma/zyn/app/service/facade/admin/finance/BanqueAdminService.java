package ma.zyn.app.service.facade.admin.finance;

import java.util.List;
import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.dao.criteria.core.finance.BanqueCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface BanqueAdminService {







	Banque create(Banque t);

    Banque update(Banque t);

    List<Banque> update(List<Banque> ts,boolean createIfNotExist);

    Banque findById(Long id);

    Banque findOrSave(Banque t);

    Banque findByReferenceEntity(Banque t);

    Banque findWithAssociatedLists(Long id);

    List<Banque> findAllOptimized();

    List<Banque> findAll();

    List<Banque> findByCriteria(BanqueCriteria criteria);

    List<Banque> findPaginatedByCriteria(BanqueCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(BanqueCriteria criteria);

    List<Banque> delete(List<Banque> ts);

    boolean deleteById(Long id);

    List<List<Banque>> getToBeSavedAndToBeDeleted(List<Banque> oldList, List<Banque> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
