package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.ModePaiement;
import ma.zyn.app.dao.criteria.core.finance.ModePaiementCriteria;
import ma.zyn.app.dao.facade.core.finance.ModePaiementDao;
import ma.zyn.app.dao.specification.core.finance.ModePaiementSpecification;
import ma.zyn.app.service.facade.admin.finance.ModePaiementAdminService;
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
public class ModePaiementAdminServiceImpl implements ModePaiementAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public ModePaiement update(ModePaiement t) {
        ModePaiement loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{ModePaiement.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public ModePaiement findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public ModePaiement findOrSave(ModePaiement t) {
        if (t != null) {
            ModePaiement result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<ModePaiement> findAll() {
        return dao.findAll();
    }

    public List<ModePaiement> findByCriteria(ModePaiementCriteria criteria) {
        List<ModePaiement> content = null;
        if (criteria != null) {
            ModePaiementSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ModePaiementSpecification constructSpecification(ModePaiementCriteria criteria) {
        ModePaiementSpecification mySpecification =  (ModePaiementSpecification) RefelexivityUtil.constructObjectUsingOneParam(ModePaiementSpecification.class, criteria);
        return mySpecification;
    }

    public List<ModePaiement> findPaginatedByCriteria(ModePaiementCriteria criteria, int page, int pageSize, String order, String sortField) {
        ModePaiementSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ModePaiementCriteria criteria) {
        ModePaiementSpecification mySpecification = constructSpecification(criteria);
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
    public List<ModePaiement> delete(List<ModePaiement> list) {
		List<ModePaiement> result = new ArrayList();
        if (list != null) {
            for (ModePaiement t : list) {
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
    public ModePaiement create(ModePaiement t) {
        ModePaiement loaded = findByReferenceEntity(t);
        ModePaiement saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public ModePaiement findWithAssociatedLists(Long id){
        ModePaiement result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<ModePaiement> update(List<ModePaiement> ts, boolean createIfNotExist) {
        List<ModePaiement> result = new ArrayList<>();
        if (ts != null) {
            for (ModePaiement t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    ModePaiement loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, ModePaiement t, ModePaiement loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public ModePaiement findByReferenceEntity(ModePaiement t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<ModePaiement> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<ModePaiement>> getToBeSavedAndToBeDeleted(List<ModePaiement> oldList, List<ModePaiement> newList) {
        List<List<ModePaiement>> result = new ArrayList<>();
        List<ModePaiement> resultDelete = new ArrayList<>();
        List<ModePaiement> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<ModePaiement> oldList, List<ModePaiement> newList, List<ModePaiement> resultUpdateOrSave, List<ModePaiement> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                ModePaiement myOld = oldList.get(i);
                ModePaiement t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                ModePaiement myNew = newList.get(i);
                ModePaiement t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    public ModePaiement findVirement() {
        return dao.findByCode("virement");
    }

    @Override
    public ModePaiement findByCode(String code) {
        return dao.findByCode(code);
    }


    public ModePaiementAdminServiceImpl(ModePaiementDao dao) {
        this.dao = dao;
    }

    private ModePaiementDao dao;
}
