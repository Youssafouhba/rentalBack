package ma.zyn.app.service.impl.admin.locaux;



import ma.zyn.app.bean.core.locaux.TypeLocal;
import ma.zyn.app.dao.criteria.core.locaux.TypeLocalCriteria;
import ma.zyn.app.dao.facade.core.locaux.TypeLocalDao;
import ma.zyn.app.dao.specification.core.locaux.TypeLocalSpecification;
import ma.zyn.app.service.facade.admin.locaux.TypeLocalAdminService;
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
public class TypeLocalAdminServiceImpl implements TypeLocalAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public TypeLocal update(TypeLocal t) {
        TypeLocal loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{TypeLocal.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public TypeLocal findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public TypeLocal findOrSave(TypeLocal t) {
        if (t != null) {
            TypeLocal result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<TypeLocal> findAll() {
        return dao.findAll();
    }

    public List<TypeLocal> findByCriteria(TypeLocalCriteria criteria) {
        List<TypeLocal> content = null;
        if (criteria != null) {
            TypeLocalSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private TypeLocalSpecification constructSpecification(TypeLocalCriteria criteria) {
        TypeLocalSpecification mySpecification =  (TypeLocalSpecification) RefelexivityUtil.constructObjectUsingOneParam(TypeLocalSpecification.class, criteria);
        return mySpecification;
    }

    public List<TypeLocal> findPaginatedByCriteria(TypeLocalCriteria criteria, int page, int pageSize, String order, String sortField) {
        TypeLocalSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(TypeLocalCriteria criteria) {
        TypeLocalSpecification mySpecification = constructSpecification(criteria);
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
    public List<TypeLocal> delete(List<TypeLocal> list) {
		List<TypeLocal> result = new ArrayList();
        if (list != null) {
            for (TypeLocal t : list) {
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
    public TypeLocal create(TypeLocal t) {
        TypeLocal loaded = findByReferenceEntity(t);
        TypeLocal saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public TypeLocal findWithAssociatedLists(Long id){
        TypeLocal result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<TypeLocal> update(List<TypeLocal> ts, boolean createIfNotExist) {
        List<TypeLocal> result = new ArrayList<>();
        if (ts != null) {
            for (TypeLocal t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    TypeLocal loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, TypeLocal t, TypeLocal loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public TypeLocal findByReferenceEntity(TypeLocal t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<TypeLocal> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<TypeLocal>> getToBeSavedAndToBeDeleted(List<TypeLocal> oldList, List<TypeLocal> newList) {
        List<List<TypeLocal>> result = new ArrayList<>();
        List<TypeLocal> resultDelete = new ArrayList<>();
        List<TypeLocal> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<TypeLocal> oldList, List<TypeLocal> newList, List<TypeLocal> resultUpdateOrSave, List<TypeLocal> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                TypeLocal myOld = oldList.get(i);
                TypeLocal t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                TypeLocal myNew = newList.get(i);
                TypeLocal t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public TypeLocalAdminServiceImpl(TypeLocalDao dao) {
        this.dao = dao;
    }

    private TypeLocalDao dao;
}
