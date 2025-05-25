package ma.zyn.app.service.impl.admin.finance;


import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.criteria.core.finance.CompteLocataireCriteria;
import ma.zyn.app.dao.facade.core.finance.CompteLocataireDao;
import ma.zyn.app.dao.specification.core.finance.CompteLocataireSpecification;
import ma.zyn.app.service.facade.admin.finance.CompteLocataireAdminService;
import ma.zyn.app.service.facade.admin.locataire.LocataireAdminService;
import ma.zyn.app.service.facade.admin.locataire.TransactionAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ma.zyn.app.zynerator.util.ListUtil.*;

@Service
public class CompteLocataireAdminServiceImpl implements CompteLocataireAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public CompteLocataire update(CompteLocataire t) {
        CompteLocataire loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{CompteLocataire.class.getSimpleName(), t.getId().toString()});
        } else {
            updateWithAssociatedLists(t);
            dao.save(t);
            return loadedItem;
        }
    }

    public CompteLocataire findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public CompteLocataire findOrSave(CompteLocataire t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            CompteLocataire result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<CompteLocataire> findAll() {
        return dao.findAll();
    }

    public List<CompteLocataire> findByCriteria(CompteLocataireCriteria criteria) {
        List<CompteLocataire> content = null;
        if (criteria != null) {
            CompteLocataireSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CompteLocataireSpecification constructSpecification(CompteLocataireCriteria criteria) {
        CompteLocataireSpecification mySpecification =  (CompteLocataireSpecification) RefelexivityUtil.constructObjectUsingOneParam(CompteLocataireSpecification.class, criteria);
        return mySpecification;
    }

    public List<CompteLocataire> findPaginatedByCriteria(CompteLocataireCriteria criteria, int page, int pageSize, String order, String sortField) {
        CompteLocataireSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CompteLocataireCriteria criteria) {
        CompteLocataireSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public CompteLocataire findByLocataireId(Long id){
        return dao.findByLocataireId(id);
    }
    public int deleteByLocataireId(Long id){
        return dao.deleteByLocataireId(id);
    }
    public long countByLocataireCode(String code){
        return dao.countByLocataireCode(code);
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
    public List<CompteLocataire> delete(List<CompteLocataire> list) {
        List<CompteLocataire> result = new ArrayList();
        if (list != null) {
            for (CompteLocataire t : list) {
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
    public CompteLocataire create(CompteLocataire t) {
        CompteLocataire loaded = findByReferenceEntity(t);
        CompteLocataire saved;
        if (loaded == null) {
            saved = dao.save(t);
            if (t.getTransactions() != null) {
                t.getTransactions().forEach(element-> {
                    transactionService.create(element);
                });
            }
        }else {
            if (t.getSolde().compareTo(BigDecimal.ZERO) >= 0) {
                loaded.addCredit(t.getSolde());
            } else {
                loaded.addDebit(t.getSolde());
            }
            saved =  dao.save(loaded);
        }
        return saved;
    }

    public CompteLocataire findWithAssociatedLists(Long id){
        CompteLocataire result = dao.findById(id).orElse(null);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CompteLocataire> update(List<CompteLocataire> ts, boolean createIfNotExist) {
        List<CompteLocataire> result = new ArrayList<>();
        if (ts != null) {
            for (CompteLocataire t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    CompteLocataire loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, CompteLocataire t, CompteLocataire loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }

    public void updateWithAssociatedLists(CompteLocataire compteLocataire){
    }








    public CompteLocataire findByReferenceEntity(CompteLocataire t) {
        return t == null ? null : findByLocataireId(t.getLocataire().getId());
    }
    public void findOrSaveAssociatedObject(CompteLocataire t){
        if( t != null) {
            t.setLocataire(locataireService.findOrSave(t.getLocataire()));
        }
    }



    public List<CompteLocataire> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<CompteLocataire>> getToBeSavedAndToBeDeleted(List<CompteLocataire> oldList, List<CompteLocataire> newList) {
        List<List<CompteLocataire>> result = new ArrayList<>();
        List<CompteLocataire> resultDelete = new ArrayList<>();
        List<CompteLocataire> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<CompteLocataire> oldList, List<CompteLocataire> newList, List<CompteLocataire> resultUpdateOrSave, List<CompteLocataire> resultDelete) {
        for (int i = 0; i < oldList.size(); i++) {
            CompteLocataire myOld = oldList.get(i);
            CompteLocataire t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
            if (t != null) {
                resultUpdateOrSave.add(t); // update
            } else {
                resultDelete.add(myOld);
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            CompteLocataire myNew = newList.get(i);
            CompteLocataire t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private TransactionAdminService transactionService ;
    @Autowired
    private LocataireAdminService locataireService ;

    public CompteLocataireAdminServiceImpl(CompteLocataireDao dao) {
        this.dao = dao;
    }

    private CompteLocataireDao dao;
}
