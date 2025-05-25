package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.TypeCharge;
import ma.zyn.app.dao.criteria.core.finance.TypeChargeCriteria;
import ma.zyn.app.dao.facade.core.finance.TypeChargeDao;
import ma.zyn.app.dao.specification.core.finance.TypeChargeSpecification;
import ma.zyn.app.service.facade.admin.finance.TypeChargeAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TypeChargeAdminServiceImpl implements TypeChargeAdminService {
    @Autowired
    private TypeChargeDao typeChargeAdminRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public TypeCharge update(TypeCharge t) {
        TypeCharge loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{TypeCharge.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public TypeCharge findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public TypeCharge findOrSave(TypeCharge t) {
        if (t != null) {
            TypeCharge result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<TypeCharge> findAll() {
        return dao.findAll();
    }

    public List<TypeCharge> findByCriteria(TypeChargeCriteria criteria) {
        List<TypeCharge> content = null;
        if (criteria != null) {
            TypeChargeSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private TypeChargeSpecification constructSpecification(TypeChargeCriteria criteria) {
        TypeChargeSpecification mySpecification =  (TypeChargeSpecification) RefelexivityUtil.constructObjectUsingOneParam(TypeChargeSpecification.class, criteria);
        return mySpecification;
    }

    public List<TypeCharge> findPaginatedByCriteria(TypeChargeCriteria criteria, int page, int pageSize, String order, String sortField) {
        TypeChargeSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(TypeChargeCriteria criteria) {
        TypeChargeSpecification mySpecification = constructSpecification(criteria);
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
    public TypeCharge findByCode(String code) {
        return dao.findByCode(code);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<TypeCharge> delete(List<TypeCharge> list) {
		List<TypeCharge> result = new ArrayList();
        if (list != null) {
            for (TypeCharge t : list) {
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
    public TypeCharge create(TypeCharge t) {
        TypeCharge loaded = findByReferenceEntity(t);
        TypeCharge saved;
        if (loaded == null) {
            saved = dao.save(t);

        }else {
            saved = null;
        }
        return saved;
    }

    public TypeCharge findWithAssociatedLists(Long id){
        TypeCharge result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<TypeCharge> update(List<TypeCharge> ts, boolean createIfNotExist) {
        List<TypeCharge> result = new ArrayList<>();
        if (ts != null) {
            for (TypeCharge t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    TypeCharge loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, TypeCharge t, TypeCharge loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public TypeCharge findByReferenceEntity(TypeCharge t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<TypeCharge> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<TypeCharge>> getToBeSavedAndToBeDeleted(List<TypeCharge> oldList, List<TypeCharge> newList) {
        List<List<TypeCharge>> result = new ArrayList<>();
        List<TypeCharge> resultDelete = new ArrayList<>();
        List<TypeCharge> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<TypeCharge> oldList, List<TypeCharge> newList, List<TypeCharge> resultUpdateOrSave, List<TypeCharge> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                TypeCharge myOld = oldList.get(i);
                TypeCharge t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                TypeCharge myNew = newList.get(i);
                TypeCharge t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public TypeChargeAdminServiceImpl(TypeChargeDao dao) {
        this.dao = dao;
    }

    private TypeChargeDao dao;
}
