package ma.zyn.app.service.impl.admin.locataire;


import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.locataire.TypeLocataire;
import ma.zyn.app.dao.criteria.core.locataire.TypeLocataireCriteria;
import ma.zyn.app.dao.facade.core.locataire.TypeLocataireDao;
import ma.zyn.app.dao.specification.core.locataire.TypeLocataireSpecification;
import ma.zyn.app.service.facade.admin.locataire.TypeLocataireAdminService;
import ma.zyn.app.zynerator.service.AbstractServiceImpl;
import static ma.zyn.app.zynerator.util.ListUtil.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.zynerator.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Service
public class TypeLocataireAdminServiceImpl implements TypeLocataireAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public TypeLocataire update(TypeLocataire t) {
        TypeLocataire loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{TypeLocataire.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public TypeLocataire findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public TypeLocataire findOrSave(TypeLocataire t) {
        if (t != null) {
            TypeLocataire result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<TypeLocataire> findAll() {
        return dao.findAll();
    }

    public List<TypeLocataire> findByCriteria(TypeLocataireCriteria criteria) {
        List<TypeLocataire> content = null;
        if (criteria != null) {
            TypeLocataireSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private TypeLocataireSpecification constructSpecification(TypeLocataireCriteria criteria) {
        TypeLocataireSpecification mySpecification =  (TypeLocataireSpecification) RefelexivityUtil.constructObjectUsingOneParam(TypeLocataireSpecification.class, criteria);
        return mySpecification;
    }

    public List<TypeLocataire> findPaginatedByCriteria(TypeLocataireCriteria criteria, int page, int pageSize, String order, String sortField) {
        TypeLocataireSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(TypeLocataireCriteria criteria) {
        TypeLocataireSpecification mySpecification = constructSpecification(criteria);
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
    public List<TypeLocataire> delete(List<TypeLocataire> list) {
		List<TypeLocataire> result = new ArrayList();
        if (list != null) {
            for (TypeLocataire t : list) {
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
    public TypeLocataire create(TypeLocataire t) {
        TypeLocataire loaded = findByReferenceEntity(t);
        TypeLocataire saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public TypeLocataire findWithAssociatedLists(Long id){
        TypeLocataire result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<TypeLocataire> update(List<TypeLocataire> ts, boolean createIfNotExist) {
        List<TypeLocataire> result = new ArrayList<>();
        if (ts != null) {
            for (TypeLocataire t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    TypeLocataire loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, TypeLocataire t, TypeLocataire loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public TypeLocataire findByReferenceEntity(TypeLocataire t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<TypeLocataire> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<TypeLocataire>> getToBeSavedAndToBeDeleted(List<TypeLocataire> oldList, List<TypeLocataire> newList) {
        List<List<TypeLocataire>> result = new ArrayList<>();
        List<TypeLocataire> resultDelete = new ArrayList<>();
        List<TypeLocataire> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<TypeLocataire> oldList, List<TypeLocataire> newList, List<TypeLocataire> resultUpdateOrSave, List<TypeLocataire> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                TypeLocataire myOld = oldList.get(i);
                TypeLocataire t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                TypeLocataire myNew = newList.get(i);
                TypeLocataire t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public TypeLocataireAdminServiceImpl(TypeLocataireDao dao) {
        this.dao = dao;
    }

    private TypeLocataireDao dao;
}
