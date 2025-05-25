package ma.zyn.app.service.impl.admin.locataire;


import ma.zyn.app.bean.core.locataire.Avoir;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.dao.criteria.core.locataire.LocataireCriteria;
import ma.zyn.app.dao.facade.core.locataire.LocataireDao;
import ma.zyn.app.dao.specification.core.locataire.LocataireSpecification;
import ma.zyn.app.service.facade.admin.finance.CompteLocataireAdminService;
import ma.zyn.app.service.facade.admin.locataire.AvoirAdminService;
import ma.zyn.app.service.facade.admin.locataire.LocataireAdminService;
import ma.zyn.app.service.facade.admin.locataire.LocationAdminService;
import ma.zyn.app.service.facade.admin.locataire.TypeLocataireAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static ma.zyn.app.zynerator.util.ListUtil.*;

@Service
public class LocataireAdminServiceImpl implements LocataireAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Locataire update(Locataire t) {
        Locataire loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Locataire.class.getSimpleName(), t.getId().toString()});
        } else {
            updateWithAssociatedLists(t);
            dao.save(t);
            return loadedItem;
        }
    }

    public Locataire findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Locataire findOrSave(Locataire t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Locataire result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Locataire> findAll() {
        return dao.findAll();
    }

    public List<Locataire> findByCriteria(LocataireCriteria criteria) {
        List<Locataire> content = null;
        if (criteria != null) {
            LocataireSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private LocataireSpecification constructSpecification(LocataireCriteria criteria) {
        LocataireSpecification mySpecification =  (LocataireSpecification) RefelexivityUtil.constructObjectUsingOneParam(LocataireSpecification.class, criteria);
        return mySpecification;
    }

    public List<Locataire> findPaginatedByCriteria(LocataireCriteria criteria, int page, int pageSize, String order, String sortField) {
        LocataireSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(LocataireCriteria criteria) {
        LocataireSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Locataire> findByTypeLocataireCode(String code){
        return dao.findByTypeLocataireCode(code);
    }
    public List<Locataire> findByTypeLocataireId(Long id){
        return dao.findByTypeLocataireId(id);
    }
    public int deleteByTypeLocataireCode(String code){
        return dao.deleteByTypeLocataireCode(code);
    }
    public int deleteByTypeLocataireId(Long id){
        return dao.deleteByTypeLocataireId(id);
    }
    public long countByTypeLocataireCode(String code){
        return dao.countByTypeLocataireCode(code);
    }
    public List<Locataire> findByCompteLocataireId(Long id){
        return dao.findByCompteLocataireId(id);
    }
    public int deleteByCompteLocataireId(Long id){
        return dao.deleteByCompteLocataireId(id);
    }
    public long countByCompteLocataireId(Long id){
        return dao.countByCompteLocataireId(id);
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
        locationService.deleteByLocataireId(id);
        compteLocataireService.deleteByLocataireId(id);
        avoirService.deleteByLocataireId(id);
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Locataire> delete(List<Locataire> list) {
        List<Locataire> result = new ArrayList();
        if (list != null) {
            for (Locataire t : list) {
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
    public Locataire create(Locataire t) {
        Locataire loaded = findByReferenceEntity(t);
        Locataire saved;
        saved = Objects.requireNonNullElseGet(loaded, () -> dao.save(t));
        if (t.getLocations() != null) {
            t.getLocations().forEach(element-> {
                element.setLocataire(saved);
                element.setActif(element.getDateDebut().getDayOfMonth() >= LocalDate.now().getDayOfMonth());
                locationService.create(element);
            });
        }
        return saved;
    }




    public Locataire findWithAssociatedLists(Long id){
        Locataire result = dao.findById(id).orElse(null);
        if(result!=null && result.getId() != null) {
            result.setLocations(locationService.findByLocataireId(id));
            result.setAvoirs(avoirService.findByLocataireId(id));
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Locataire> update(List<Locataire> ts, boolean createIfNotExist) {
        List<Locataire> result = new ArrayList<>();
        if (ts != null) {
            for (Locataire t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Locataire loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Locataire t, Locataire loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }

    public void updateWithAssociatedLists(Locataire locataire){
        if(locataire !=null && locataire.getId() != null){
            List<List<Location>> resultLocations= locationService.getToBeSavedAndToBeDeleted(locationService.findByLocataireId(locataire.getId()),locataire.getLocations());
            locationService.delete(resultLocations.get(1));
            emptyIfNull(resultLocations.get(0)).forEach(e -> e.setLocataire(locataire));
            locationService.update(resultLocations.get(0),true);
            List<List<Avoir>> resultAvoirs= avoirService.getToBeSavedAndToBeDeleted(avoirService.findByLocataireId(locataire.getId()),locataire.getAvoirs());
            avoirService.delete(resultAvoirs.get(1));
            emptyIfNull(resultAvoirs.get(0)).forEach(e -> e.setLocataire(locataire));
            avoirService.update(resultAvoirs.get(0),true);
        }
    }








    public Locataire findByReferenceEntity(Locataire t){
        return t==null? null : dao.findLocataireByNomAndPrenomAndTelephone(t.getNom(),t.getPrenom(),t.getTelephone());
    }

    public void findOrSaveAssociatedObject(Locataire t){
        if( t != null) {
            t.setTypeLocataire(typeLocataireService.findOrSave(t.getTypeLocataire()));
            t.setCompteLocataire(compteLocataireService.findOrSave(t.getCompteLocataire()));
        }
    }



    public List<Locataire> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Locataire>> getToBeSavedAndToBeDeleted(List<Locataire> oldList, List<Locataire> newList) {
        List<List<Locataire>> result = new ArrayList<>();
        List<Locataire> resultDelete = new ArrayList<>();
        List<Locataire> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Locataire> oldList, List<Locataire> newList, List<Locataire> resultUpdateOrSave, List<Locataire> resultDelete) {
        for (int i = 0; i < oldList.size(); i++) {
            Locataire myOld = oldList.get(i);
            Locataire t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
            if (t != null) {
                resultUpdateOrSave.add(t); // update
            } else {
                resultDelete.add(myOld);
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            Locataire myNew = newList.get(i);
            Locataire t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private AvoirAdminService avoirService ;
    @Autowired
    private CompteLocataireAdminService compteLocataireService ;
    @Autowired
    private TypeLocataireAdminService typeLocataireService ;
    @Autowired
    private LocationAdminService locationService ;

    public LocataireAdminServiceImpl(LocataireDao dao) {
        this.dao = dao;
    }

    private LocataireDao dao;
}
