package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.service.facade.admin.finance.CompteAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.dao.criteria.core.finance.BanqueCriteria;
import ma.zyn.app.dao.facade.core.finance.BanqueDao;
import ma.zyn.app.dao.specification.core.finance.BanqueSpecification;
import ma.zyn.app.service.facade.admin.finance.BanqueAdminService;

import static ma.zyn.app.zynerator.util.ListUtil.*;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.zynerator.util.RefelexivityUtil;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
public class BanqueAdminServiceImpl implements BanqueAdminService {

    private final CompteAdminService compteAdminService;


    public BanqueAdminServiceImpl(CompteAdminService compteAdminService, BanqueDao dao) {
        this.compteAdminService = compteAdminService;
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Banque update(Banque t) {
        Banque loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Banque.class.getSimpleName(), t.getId().toString()});
        } else {
            Banque saved = dao.save(t);

            return loadedItem;
        }
    }

    public Banque findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Banque findOrSave(Banque t) {
        if (t != null) {
            Banque result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Banque> findAll() {
        return dao.findAll();
    }

    public List<Banque> findByCriteria(BanqueCriteria criteria) {
        List<Banque> content = null;
        if (criteria != null) {
            BanqueSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private BanqueSpecification constructSpecification(BanqueCriteria criteria) {
        BanqueSpecification mySpecification =  (BanqueSpecification) RefelexivityUtil.constructObjectUsingOneParam(BanqueSpecification.class, criteria);
        return mySpecification;
    }

    public List<Banque> findPaginatedByCriteria(BanqueCriteria criteria, int page, int pageSize, String order, String sortField) {
        BanqueSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(BanqueCriteria criteria) {
        BanqueSpecification mySpecification = constructSpecification(criteria);
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
    public List<Banque> delete(List<Banque> list) {
		List<Banque> result = new ArrayList();
        if (list != null) {
            for (Banque t : list) {
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
    public Banque create(Banque t) {
        t.setCode(t.getNom());
        Banque loaded = findByReferenceEntity(t);
        Banque saved;
        if (loaded == null) {
            saved = dao.save(t);
            createCompte(saved);
        }else {
            saved = null;
        }
        return saved;
    }

    public void createCompte(Banque t) {
        Compte compte = new Compte();
        BigDecimal solde = t.getSolde();
        compte.initialize(solde);
        compte.setBanque(t);
        compte.setCode(t.getNom());
        compte.setDateCreation(t.getDateCreation());
        Compte saved = compteAdminService.create(compte);
    }

    public Banque findWithAssociatedLists(Long id){
        Banque result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Banque> update(List<Banque> ts, boolean createIfNotExist) {
        List<Banque> result = new ArrayList<>();
        if (ts != null) {
            for (Banque t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Banque loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Banque t, Banque loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Banque findByReferenceEntity(Banque t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<Banque> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Banque>> getToBeSavedAndToBeDeleted(List<Banque> oldList, List<Banque> newList) {
        List<List<Banque>> result = new ArrayList<>();
        List<Banque> resultDelete = new ArrayList<>();
        List<Banque> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Banque> oldList, List<Banque> newList, List<Banque> resultUpdateOrSave, List<Banque> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Banque myOld = oldList.get(i);
                Banque t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Banque myNew = newList.get(i);
                Banque t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }










    private BanqueDao dao;
}
