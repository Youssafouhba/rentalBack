package ma.zyn.app.service.impl.admin.locataire;


import ma.zyn.app.bean.core.locataire.TypeTransactiont;
import ma.zyn.app.dao.criteria.core.locataire.TypeTransactiontCriteria;
import ma.zyn.app.dao.facade.core.locataire.TypeTransactiontDao;
import ma.zyn.app.dao.specification.core.locataire.TypeTransactiontSpecification;
import ma.zyn.app.service.facade.admin.locataire.TypeTransactiontAdminService;
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
public class TypeTransactiontAdminServiceImpl implements TypeTransactiontAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public TypeTransactiont update(TypeTransactiont t) {
        TypeTransactiont loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{TypeTransactiont.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public TypeTransactiont findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public TypeTransactiont findOrSave(TypeTransactiont t) {
        if (t != null) {
            TypeTransactiont result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<TypeTransactiont> findAll() {
        return dao.findAll();
    }

    public List<TypeTransactiont> findByCriteria(TypeTransactiontCriteria criteria) {
        List<TypeTransactiont> content = null;
        if (criteria != null) {
            TypeTransactiontSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private TypeTransactiontSpecification constructSpecification(TypeTransactiontCriteria criteria) {
        TypeTransactiontSpecification mySpecification =  (TypeTransactiontSpecification) RefelexivityUtil.constructObjectUsingOneParam(TypeTransactiontSpecification.class, criteria);
        return mySpecification;
    }

    public List<TypeTransactiont> findPaginatedByCriteria(TypeTransactiontCriteria criteria, int page, int pageSize, String order, String sortField) {
        TypeTransactiontSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(TypeTransactiontCriteria criteria) {
        TypeTransactiontSpecification mySpecification = constructSpecification(criteria);
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




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<TypeTransactiont> delete(List<TypeTransactiont> list) {
		List<TypeTransactiont> result = new ArrayList();
        if (list != null) {
            for (TypeTransactiont t : list) {
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
    public TypeTransactiont create(TypeTransactiont t) {
        TypeTransactiont loaded = findByReferenceEntity(t);
        TypeTransactiont saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public TypeTransactiont findWithAssociatedLists(Long id){
        TypeTransactiont result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<TypeTransactiont> update(List<TypeTransactiont> ts, boolean createIfNotExist) {
        List<TypeTransactiont> result = new ArrayList<>();
        if (ts != null) {
            for (TypeTransactiont t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    TypeTransactiont loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, TypeTransactiont t, TypeTransactiont loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public TypeTransactiont findByReferenceEntity(TypeTransactiont t){
        return t==null? null : dao.findByCode(t.getCode());
    }

    @Override
    public TypeTransactiont findReglement(){
        return dao.findByCode("reglement");
    }

    @Override
    public TypeTransactiont findCharge(){
        return dao.findByCode("charge");
    }
    @Override
    public TypeTransactiont findAvoir(){
        return dao.findByCode("avoir");
    }
    public List<TypeTransactiont> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<TypeTransactiont>> getToBeSavedAndToBeDeleted(List<TypeTransactiont> oldList, List<TypeTransactiont> newList) {
        List<List<TypeTransactiont>> result = new ArrayList<>();
        List<TypeTransactiont> resultDelete = new ArrayList<>();
        List<TypeTransactiont> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<TypeTransactiont> oldList, List<TypeTransactiont> newList, List<TypeTransactiont> resultUpdateOrSave, List<TypeTransactiont> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                TypeTransactiont myOld = oldList.get(i);
                TypeTransactiont t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                TypeTransactiont myNew = newList.get(i);
                TypeTransactiont t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public TypeTransactiontAdminServiceImpl(TypeTransactiontDao dao) {
        this.dao = dao;
    }

    private TypeTransactiontDao dao;
}
