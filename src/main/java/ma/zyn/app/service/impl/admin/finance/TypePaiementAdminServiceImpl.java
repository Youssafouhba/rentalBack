package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.TypePaiement;
import ma.zyn.app.dao.criteria.core.finance.TypePaiementCriteria;
import ma.zyn.app.dao.facade.core.finance.TypePaiementDao;
import ma.zyn.app.dao.specification.core.finance.TypePaiementSpecification;
import ma.zyn.app.service.facade.admin.finance.TypePaiementAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static ma.zyn.app.zynerator.util.ListUtil.isEmpty;
import static ma.zyn.app.zynerator.util.ListUtil.isNotEmpty;

@Service
public class TypePaiementAdminServiceImpl implements TypePaiementAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public TypePaiement update(TypePaiement t) {
        TypePaiement loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{TypePaiement.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public TypePaiement findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public TypePaiement findOrSave(TypePaiement t) {
        if (t != null) {
            TypePaiement result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<TypePaiement> findAll() {
        return dao.findAll();
    }

    public List<TypePaiement> findByCriteria(TypePaiementCriteria criteria) {
        List<TypePaiement> content = null;
        if (criteria != null) {
            TypePaiementSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private TypePaiementSpecification constructSpecification(TypePaiementCriteria criteria) {
        TypePaiementSpecification mySpecification =  (TypePaiementSpecification) RefelexivityUtil.constructObjectUsingOneParam(TypePaiementSpecification.class, criteria);
        return mySpecification;
    }

    public List<TypePaiement> findPaginatedByCriteria(TypePaiementCriteria criteria, int page, int pageSize, String order, String sortField) {
        TypePaiementSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(TypePaiementCriteria criteria) {
        TypePaiementSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }

    @Override
    public TypePaiement findCredit(){
        return dao.findByCode("Credit");
    }

    @Override
    public TypePaiement findDebit(){
        return dao.findByCode("Debit");
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<TypePaiement> delete(List<TypePaiement> list) {
		List<TypePaiement> result = new ArrayList();
        if (list != null) {
            for (TypePaiement t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public TypePaiement create(TypePaiement t) {
        TypePaiement loaded = findByReferenceEntity(t);
        TypePaiement saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public TypePaiement findWithAssociatedLists(Long id){
        TypePaiement result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<TypePaiement> update(List<TypePaiement> ts, boolean createIfNotExist) {
        List<TypePaiement> result = new ArrayList<>();
        if (ts != null) {
            for (TypePaiement t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    TypePaiement loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, TypePaiement t, TypePaiement loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public TypePaiement findByReferenceEntity(TypePaiement t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<TypePaiement> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<TypePaiement>> getToBeSavedAndToBeDeleted(List<TypePaiement> oldList, List<TypePaiement> newList) {
        List<List<TypePaiement>> result = new ArrayList<>();
        List<TypePaiement> resultDelete = new ArrayList<>();
        List<TypePaiement> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<TypePaiement> oldList, List<TypePaiement> newList, List<TypePaiement> resultUpdateOrSave, List<TypePaiement> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                TypePaiement myOld = oldList.get(i);
                TypePaiement t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                TypePaiement myNew = newList.get(i);
                TypePaiement t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public TypePaiementAdminServiceImpl(TypePaiementDao dao) {
        this.dao = dao;
    }

    private TypePaiementDao dao;
}
