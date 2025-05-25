package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.CompteAdmin;
import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.dao.criteria.core.finance.CompteAdminCriteria;
import ma.zyn.app.dao.facade.core.finance.CompteAdminDao;
import ma.zyn.app.dao.specification.core.finance.CompteAdminSpecification;
import ma.zyn.app.service.facade.admin.finance.CompteAdminAdminService;
import ma.zyn.app.service.facade.admin.finance.CompteAdminService;
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

import static ma.zyn.app.zynerator.util.ListUtil.*;

@Service
public class CompteAdminAdminServiceImpl implements CompteAdminAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public CompteAdmin update(CompteAdmin t) {
        CompteAdmin loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{CompteAdmin.class.getSimpleName(), t.getId().toString()});
        } else {
            updateWithAssociatedLists(t);
            dao.save(t);
            return loadedItem;
        }
    }

    public CompteAdmin findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public CompteAdmin findOrSave(CompteAdmin t) {
        if (t != null) {
            CompteAdmin result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<CompteAdmin> findAll() {
        return dao.findAll();
    }

    public List<CompteAdmin> findByCriteria(CompteAdminCriteria criteria) {
        List<CompteAdmin> content = null;
        if (criteria != null) {
            CompteAdminSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CompteAdminSpecification constructSpecification(CompteAdminCriteria criteria) {
        CompteAdminSpecification mySpecification =  (CompteAdminSpecification) RefelexivityUtil.constructObjectUsingOneParam(CompteAdminSpecification.class, criteria);
        return mySpecification;
    }

    public List<CompteAdmin> findPaginatedByCriteria(CompteAdminCriteria criteria, int page, int pageSize, String order, String sortField) {
        CompteAdminSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CompteAdminCriteria criteria) {
        CompteAdminSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            deleteAssociatedLists(id);
            dao.deleteById(id);
        }
        return condition;
    }

    public void deleteAssociatedLists(Long id) {
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CompteAdmin> delete(List<CompteAdmin> list) {
		List<CompteAdmin> result = new ArrayList();
        if (list != null) {
            for (CompteAdmin t : list) {
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
    public CompteAdmin create(CompteAdmin t) {
        CompteAdmin loaded = findByReferenceEntity(t);
        CompteAdmin saved;
        if (loaded == null) {
            saved = dao.save(t);
            if (t.getComptes() != null) {
                t.getComptes().forEach(element-> {
                    compteService.create(element);
                });
            }
        }else {
            saved = null;
        }
        return saved;
    }

    public CompteAdmin findWithAssociatedLists(Long id){
        CompteAdmin result = dao.findById(id).orElse(null);
        if(result!=null && result.getId() != null) {
        }
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CompteAdmin> update(List<CompteAdmin> ts, boolean createIfNotExist) {
        List<CompteAdmin> result = new ArrayList<>();
        if (ts != null) {
            for (CompteAdmin t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    CompteAdmin loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, CompteAdmin t, CompteAdmin loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }

    public void updateWithAssociatedLists(CompteAdmin compteAdmin){
    }








    public CompteAdmin findByReferenceEntity(CompteAdmin t) {
        return t == null  ? null : findAdmin();
    }
    @Override
    public CompteAdmin findAdmin() {
        return dao.findByCode("ADMIN");
    }


    public List<CompteAdmin> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<CompteAdmin>> getToBeSavedAndToBeDeleted(List<CompteAdmin> oldList, List<CompteAdmin> newList) {
        List<List<CompteAdmin>> result = new ArrayList<>();
        List<CompteAdmin> resultDelete = new ArrayList<>();
        List<CompteAdmin> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<CompteAdmin> oldList, List<CompteAdmin> newList, List<CompteAdmin> resultUpdateOrSave, List<CompteAdmin> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                CompteAdmin myOld = oldList.get(i);
                CompteAdmin t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                CompteAdmin myNew = newList.get(i);
                CompteAdmin t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }




    @Autowired
    private CompteAdminService compteService ;

    public CompteAdminAdminServiceImpl(CompteAdminDao dao) {
        this.dao = dao;
    }

    private CompteAdminDao dao;
}
