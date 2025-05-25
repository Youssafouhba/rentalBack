package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.dao.criteria.core.finance.CaisseCriteria;
import ma.zyn.app.dao.facade.core.finance.CaisseDao;
import ma.zyn.app.dao.specification.core.finance.CaisseSpecification;
import ma.zyn.app.service.facade.admin.finance.CaisseAdminService;
import ma.zyn.app.service.facade.admin.finance.CompteAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ma.zyn.app.zynerator.util.ListUtil.isEmpty;
import static ma.zyn.app.zynerator.util.ListUtil.isNotEmpty;
@Service
public class CaisseAdminServiceImpl implements CaisseAdminService {

    private final CompteAdminService compteAdminService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Caisse update(Caisse t) {
        Caisse loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Caisse.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Caisse findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Caisse findOrSave(Caisse t) {
        if (t != null) {
            Caisse result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Caisse> findAll() {
        return dao.findAll();
    }

    public List<Caisse> findByCriteria(CaisseCriteria criteria) {
        List<Caisse> content = null;
        if (criteria != null) {
            CaisseSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CaisseSpecification constructSpecification(CaisseCriteria criteria) {
        CaisseSpecification mySpecification =  (CaisseSpecification) RefelexivityUtil.constructObjectUsingOneParam(CaisseSpecification.class, criteria);
        return mySpecification;
    }

    public List<Caisse> findPaginatedByCriteria(CaisseCriteria criteria, int page, int pageSize, String order, String sortField) {
        CaisseSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CaisseCriteria criteria) {
        CaisseSpecification mySpecification = constructSpecification(criteria);
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
    public List<Caisse> delete(List<Caisse> list) {
		List<Caisse> result = new ArrayList();
        if (list != null) {
            for (Caisse t : list) {
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
    public Caisse create(Caisse t) {
        t.setCode(t.getLibelle());
        Caisse loaded = findByReferenceEntity(t);
        Caisse saved;
        if (loaded == null) {
            saved = dao.save(t);
            createCompte(saved);
        }else {
            saved = null;
        }

        return saved;
    }

    public void createCompte(Caisse t) {
        Compte compte = new Compte();
        compte.setCaisse(t);
        compte.setCode(t.getLibelle());
        compte.setDateCreation(t.getDateCreation());
        BigDecimal solde = t.getSolde();
        compte.initialize(solde);
        Compte saved = compteAdminService.create(compte);
    }
    public Caisse findWithAssociatedLists(Long id){
        Caisse result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Caisse> update(List<Caisse> ts, boolean createIfNotExist) {
        List<Caisse> result = new ArrayList<>();
        if (ts != null) {
            for (Caisse t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Caisse loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Caisse t, Caisse loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Caisse findByReferenceEntity(Caisse t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<Caisse> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Caisse>> getToBeSavedAndToBeDeleted(List<Caisse> oldList, List<Caisse> newList) {
        List<List<Caisse>> result = new ArrayList<>();
        List<Caisse> resultDelete = new ArrayList<>();
        List<Caisse> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Caisse> oldList, List<Caisse> newList, List<Caisse> resultUpdateOrSave, List<Caisse> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Caisse myOld = oldList.get(i);
                Caisse t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Caisse myNew = newList.get(i);
                Caisse t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public CaisseAdminServiceImpl(CaisseDao dao, CompteAdminServiceImpl compteAdminService) {
        this.dao = dao;
        this.compteAdminService = compteAdminService;
    }

    private CaisseDao dao;
}
