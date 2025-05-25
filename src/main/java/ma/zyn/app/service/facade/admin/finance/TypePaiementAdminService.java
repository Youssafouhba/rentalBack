package ma.zyn.app.service.facade.admin.finance;

import ma.zyn.app.bean.core.finance.TypePaiement;
import ma.zyn.app.dao.criteria.core.finance.TypePaiementCriteria;

import java.util.List;


public interface TypePaiementAdminService {







	TypePaiement create(TypePaiement t);

    TypePaiement update(TypePaiement t);

    List<TypePaiement> update(List<TypePaiement> ts,boolean createIfNotExist);

    TypePaiement findById(Long id);

    TypePaiement findOrSave(TypePaiement t);

    TypePaiement findByReferenceEntity(TypePaiement t);

    TypePaiement findWithAssociatedLists(Long id);

    List<TypePaiement> findAllOptimized();

    List<TypePaiement> findAll();

    List<TypePaiement> findByCriteria(TypePaiementCriteria criteria);

    List<TypePaiement> findPaginatedByCriteria(TypePaiementCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(TypePaiementCriteria criteria);

    TypePaiement findDebit();

    List<TypePaiement> delete(List<TypePaiement> ts);

    boolean deleteById(Long id);

    List<List<TypePaiement>> getToBeSavedAndToBeDeleted(List<TypePaiement> oldList, List<TypePaiement> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;


    TypePaiement findCredit();
}
