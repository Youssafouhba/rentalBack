package ma.zyn.app.service.impl.admin.locataire;


import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.dao.criteria.core.locataire.LocationCriteria;
import ma.zyn.app.dao.facade.core.locataire.LocationDao;
import ma.zyn.app.dao.specification.core.locataire.LocationSpecification;
import ma.zyn.app.service.facade.admin.locataire.LocataireAdminService;
import ma.zyn.app.service.facade.admin.locataire.LocationAdminService;
import ma.zyn.app.service.facade.admin.locataire.StatutLocalAdminService;
import ma.zyn.app.service.facade.admin.locaux.LocalAdminService;
import ma.zyn.app.service.impl.admin.finance.CompteLocataireAdminServiceImpl;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ma.zyn.app.zynerator.util.ListUtil.isEmpty;
import static ma.zyn.app.zynerator.util.ListUtil.isNotEmpty;

@Service
public class LocationAdminServiceImpl implements LocationAdminService {

    @Autowired
    private CompteLocataireAdminServiceImpl compteLocataireAdminService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Location update(Location t) {
        Location loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Location.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Location findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Location findOrSave(Location t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Location result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Location> findAll() {
        return dao.findAll();
    }

    public List<Location> findByCriteria(LocationCriteria criteria) {
        List<Location> content = null;
        if (criteria != null) {
            LocationSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private LocationSpecification constructSpecification(LocationCriteria criteria) {
        LocationSpecification mySpecification =  (LocationSpecification) RefelexivityUtil.constructObjectUsingOneParam(LocationSpecification.class, criteria);
        return mySpecification;
    }

    public List<Location> findPaginatedByCriteria(LocationCriteria criteria, int page, int pageSize, String order, String sortField) {
        LocationSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(LocationCriteria criteria) {
        LocationSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Location> findByLocataireId(Long id){
        return dao.findByLocataireId(id);
    }
    public int deleteByLocataireId(Long id){
        return dao.deleteByLocataireId(id);
    }
    public long countByLocataireCode(String code){
        return dao.countByLocataireCode(code);
    }
    public List<Location> findByLocalId(Long id){
        return dao.findByLocalId(id);
    }
    public int deleteByLocalId(Long id){
        return dao.deleteByLocalId(id);
    }
    public long countByLocalCode(String code){
        return dao.countByLocalCode(code);
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
    public List<Location> delete(List<Location> list) {
		List<Location> result = new ArrayList();
        if (list != null) {
            for (Location t : list) {
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
    public Location create(Location t) {
        Location loaded = findByReferenceEntity(t);
        Location saved;
        if (loaded == null) {
            findOrSaveAssociatedObject(t);
            saved = dao.save(t);
            createCompteLocataire(t);
        }else {
            saved = null;
        }
        return saved;
    }


    public void createCompteLocataire(Location location) {
        CompteLocataire compte = new CompteLocataire();
        compte.setLocataire(location.getLocataire());
        BigDecimal solde = location.getSolde();            // montant du solde
        compte.setDateCreation(location.getDateDebut());
        compte.initialize(solde);
        compteLocataireAdminService.create(compte);
    }




    public Location findWithAssociatedLists(Long id){
        Location result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Location> update(List<Location> ts, boolean createIfNotExist) {
        List<Location> result = new ArrayList<>();
        if (ts != null) {
            for (Location t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Location loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Location t, Location loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Location findByReferenceEntity(Location t){
        return t==null? null : dao.findByCode(t.getCode());
    }

    public void findOrSaveAssociatedObject(Location t) {
        if (t != null) {
            LocalDateTime dateActuel = LocalDateTime.now();

            Local orSave = localService.findOrSave(t.getLocal());

            if (t.getDateDebut() != null) {
                boolean isActive;

                if (t.getDateFin() != null) {
                    // Cas avec date de fin
                    isActive = !dateActuel.isBefore(t.getDateDebut()) && !dateActuel.isAfter(t.getDateFin());
                } else {
                    // Cas sans date de fin : actif tant que date actuelle >= dateDebut
                    isActive = !dateActuel.isBefore(t.getDateDebut());
                }

                if (isActive) {
                    orSave.setStatutLocal(statutLocalService.findAllowed());
                }
            }

            t.setLocal(localService.update(orSave));
        }
    }



    public List<Location> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Location>> getToBeSavedAndToBeDeleted(List<Location> oldList, List<Location> newList) {
        List<List<Location>> result = new ArrayList<>();
        List<Location> resultDelete = new ArrayList<>();
        List<Location> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Location> oldList, List<Location> newList, List<Location> resultUpdateOrSave, List<Location> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Location myOld = oldList.get(i);
                Location t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Location myNew = newList.get(i);
                Location t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private LocalAdminService localService ;
    @Autowired
    private LocataireAdminService locataireService ;
    @Autowired
    private StatutLocalAdminService statutLocalService ;

    public LocationAdminServiceImpl(LocationDao dao) {
        this.dao = dao;
    }

    private LocationDao dao;
}
