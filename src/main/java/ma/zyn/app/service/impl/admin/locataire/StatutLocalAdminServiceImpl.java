package ma.zyn.app.service.impl.admin.locataire;


import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.locataire.StatutLocal;
import ma.zyn.app.dao.criteria.core.locataire.StatutLocalCriteria;
import ma.zyn.app.dao.facade.core.locataire.StatutLocalDao;
import ma.zyn.app.dao.specification.core.locataire.StatutLocalSpecification;
import ma.zyn.app.service.facade.admin.locataire.StatutLocalAdminService;
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
public class StatutLocalAdminServiceImpl implements StatutLocalAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public StatutLocal update(StatutLocal t) {
        StatutLocal loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{StatutLocal.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public StatutLocal findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public StatutLocal findOrSave(StatutLocal t) {
        if (t != null) {
            StatutLocal result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<StatutLocal> findAll() {
        return dao.findAll();
    }

    public List<StatutLocal> findByCriteria(StatutLocalCriteria criteria) {
        List<StatutLocal> content = null;
        if (criteria != null) {
            StatutLocalSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private StatutLocalSpecification constructSpecification(StatutLocalCriteria criteria) {
        StatutLocalSpecification mySpecification =  (StatutLocalSpecification) RefelexivityUtil.constructObjectUsingOneParam(StatutLocalSpecification.class, criteria);
        return mySpecification;
    }

    public List<StatutLocal> findPaginatedByCriteria(StatutLocalCriteria criteria, int page, int pageSize, String order, String sortField) {
        StatutLocalSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(StatutLocalCriteria criteria) {
        StatutLocalSpecification mySpecification = constructSpecification(criteria);
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
    public List<StatutLocal> delete(List<StatutLocal> list) {
		List<StatutLocal> result = new ArrayList();
        if (list != null) {
            for (StatutLocal t : list) {
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
    public StatutLocal create(StatutLocal t) {
        StatutLocal loaded = findByReferenceEntity(t);
        StatutLocal saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public StatutLocal findWithAssociatedLists(Long id){
        StatutLocal result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<StatutLocal> update(List<StatutLocal> ts, boolean createIfNotExist) {
        List<StatutLocal> result = new ArrayList<>();
        if (ts != null) {
            for (StatutLocal t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    StatutLocal loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, StatutLocal t, StatutLocal loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public StatutLocal findByReferenceEntity(StatutLocal t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<StatutLocal> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<StatutLocal>> getToBeSavedAndToBeDeleted(List<StatutLocal> oldList, List<StatutLocal> newList) {
        List<List<StatutLocal>> result = new ArrayList<>();
        List<StatutLocal> resultDelete = new ArrayList<>();
        List<StatutLocal> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<StatutLocal> oldList, List<StatutLocal> newList, List<StatutLocal> resultUpdateOrSave, List<StatutLocal> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                StatutLocal myOld = oldList.get(i);
                StatutLocal t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                StatutLocal myNew = newList.get(i);
                StatutLocal t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }

    @Override
    public StatutLocal findByCode(String code) {
        return dao.findByCode(code);
    }

    @Override
    public StatutLocal findAllowed() {
        return dao.findByCode("loué");
    }


    public StatutLocalAdminServiceImpl(StatutLocalDao dao) {
        this.dao = dao;
    }

    private StatutLocalDao dao;
}
