package ma.zyn.app.service.impl.admin.locaux;


import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.dao.criteria.core.locaux.LocalCriteria;
import ma.zyn.app.dao.facade.core.locaux.LocalDao;
import ma.zyn.app.dao.specification.core.locaux.LocalSpecification;
import ma.zyn.app.service.facade.admin.locataire.LocationAdminService;
import ma.zyn.app.service.facade.admin.locataire.StatutLocalAdminService;
import ma.zyn.app.service.facade.admin.locaux.LocalAdminService;
import ma.zyn.app.service.facade.admin.locaux.TypeLocalAdminService;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ma.zyn.app.zynerator.util.ListUtil.*;

@Service
public class LocalAdminServiceImpl implements LocalAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Local update(Local t) {
        Local loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Local.class.getSimpleName(), t.getId().toString()});
        } else {
            updateWithAssociatedLists(t);
            dao.save(t);
            return loadedItem;
        }
    }



    public Local findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Local findOrSave(Local t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Local result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Local> findAll() {
        return dao.findAll();
    }

    public List<Local> findByCriteria(LocalCriteria criteria) {
        List<Local> content = null;
        if (criteria != null) {
            LocalSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private LocalSpecification constructSpecification(LocalCriteria criteria) {
        LocalSpecification mySpecification =  (LocalSpecification) RefelexivityUtil.constructObjectUsingOneParam(LocalSpecification.class, criteria);
        return mySpecification;
    }

    public List<Local> findPaginatedByCriteria(LocalCriteria criteria, int page, int pageSize, String order, String sortField) {
        LocalSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(LocalCriteria criteria) {
        LocalSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Local> findByStatutLocalCode(String code){
        return dao.findByStatutLocalCode(code);
    }
    public List<Local> findByStatutLocalId(Long id){
        return dao.findByStatutLocalId(id);
    }
    public int deleteByStatutLocalCode(String code){
        return dao.deleteByStatutLocalCode(code);
    }
    public int deleteByStatutLocalId(Long id){
        return dao.deleteByStatutLocalId(id);
    }
    public long countByStatutLocalCode(String code){
        return dao.countByStatutLocalCode(code);
    }
    public List<Local> findByTypeLocalCode(String code){
        return dao.findByTypeLocalCode(code);
    }
    public List<Local> findByTypeLocalId(Long id){
        return dao.findByTypeLocalId(id);
    }
    public int deleteByTypeLocalCode(String code){
        return dao.deleteByTypeLocalCode(code);
    }
    public int deleteByTypeLocalId(Long id){
        return dao.deleteByTypeLocalId(id);
    }
    public long countByTypeLocalCode(String code){
        return dao.countByTypeLocalCode(code);
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
        locationService.deleteByLocalId(id);
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Local> delete(List<Local> list) {
		List<Local> result = new ArrayList();
        if (list != null) {
            for (Local t : list) {
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
    public Local create(Local t) {
        Local loaded = findByReferenceEntity(t);
        Local saved;
        if (loaded == null) {
            saved = dao.save(t);
            if (t.getLocations() != null) {
                t.getLocations().forEach(element-> {
                    element.setLocal(saved);
                    locationService.create(element);
                });
            }
        }else {
            saved = null;
        }
        return saved;
    }

    public Local findWithAssociatedLists(Long id){
        Local result = dao.findById(id).orElse(null);
        if(result!=null && result.getId() != null) {
            result.setLocations(locationService.findByLocalId(id));
        }
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Local> update(List<Local> ts, boolean createIfNotExist) {
        List<Local> result = new ArrayList<>();
        if (ts != null) {
            for (Local t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Local loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Local t, Local loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }


    public void updateWithAssociatedLists(Local local) {
        if (local != null && local.getId() != null) {
            List<List<Location>> resultLocations = locationService.getToBeSavedAndToBeDeleted(
                    locationService.findByLocalId(local.getId()),
                    local.getLocations()
            );

            List<Location> toDelete = resultLocations.get(1);
            List<Location> toSave = resultLocations.get(0);

            // Mettre à jour la date de fin des anciennes locations
            LocalDateTime now = LocalDateTime.now();
            if(local.getStatutLocal().getCode().equals("disponible")){
                for (Location loc : toSave) {
                    loc.setDateFin(now);
                    locationService.update(loc); // Important : il faut persister le changement avant suppression si nécessaire
                }
            }

            // Supprimer les anciennes locations (si suppression physique)
            locationService.delete(toDelete);

            // Associer les nouvelles locations au local
            emptyIfNull(toSave).forEach(e -> e.setLocal(local));
            locationService.update(toSave, true);
        }
    }









    public Local findByReferenceEntity(Local t){
        return t==null? null : dao.findByCode(t.getCode());
    }
    public void findOrSaveAssociatedObject(Local t){
        if( t != null) {
            t.setStatutLocal(statutLocalService.findOrSave(t.getStatutLocal()));
            t.setTypeLocal(typeLocalService.findOrSave(t.getTypeLocal()));
        }
    }



    public List<Local> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Local>> getToBeSavedAndToBeDeleted(List<Local> oldList, List<Local> newList) {
        List<List<Local>> result = new ArrayList<>();
        List<Local> resultDelete = new ArrayList<>();
        List<Local> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Local> oldList, List<Local> newList, List<Local> resultUpdateOrSave, List<Local> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Local myOld = oldList.get(i);
                Local t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Local myNew = newList.get(i);
                Local t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private StatutLocalAdminService statutLocalService ;
    @Autowired
    private TypeLocalAdminService typeLocalService ;
    @Autowired
    private LocationAdminService locationService ;

    public LocalAdminServiceImpl(LocalDao dao) {
        this.dao = dao;
    }

    private LocalDao dao;
}
