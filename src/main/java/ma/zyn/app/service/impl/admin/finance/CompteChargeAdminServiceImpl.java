package ma.zyn.app.service.impl.admin.finance;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ma.zyn.app.bean.core.finance.*;
import ma.zyn.app.dao.criteria.core.finance.CompteChargeCriteria;
import ma.zyn.app.dao.facade.core.finance.CompteChargeDao;
import ma.zyn.app.dao.specification.core.finance.CompteChargeSpecification;
import ma.zyn.app.service.facade.admin.finance.*;
import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.zynerator.util.ListUtil;
import ma.zyn.app.zynerator.util.RefelexivityUtil;
import org.apache.commons.lang.RandomStringUtils;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ma.zyn.app.zynerator.util.ListUtil.*;

@Service
public class CompteChargeAdminServiceImpl implements CompteChargeAdminService {
    private final CompteAdminService compteAdminService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CompteAdminAdminService compteAdminAdminService;
    @Autowired
    private TypeChargeAdminService typeChargeAdminService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public CompteCharge update(CompteCharge t) {
        CompteCharge loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{CompteCharge.class.getSimpleName(), t.getId().toString()});
        } else {
            //updateWithAssociatedLists(t);
            dao.save(t);
            return loadedItem;
        }
    }

    public CompteCharge findById(Long id) {
        return dao.findById(id).orElse(null);
    }



    public CompteCharge findOrSave(CompteCharge t) {
        if (t != null) {
            CompteCharge result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<CompteCharge> findAll() {
        return dao.findAll();
    }

    public List<CompteCharge> findByCriteria(CompteChargeCriteria criteria) {
        List<CompteCharge> content = null;
        if (criteria != null) {
            CompteChargeSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CompteChargeSpecification constructSpecification(CompteChargeCriteria criteria) {
        CompteChargeSpecification mySpecification =  (CompteChargeSpecification) RefelexivityUtil.constructObjectUsingOneParam(CompteChargeSpecification.class, criteria);
        return mySpecification;
    }

    public List<CompteCharge> findPaginatedByCriteria(CompteChargeCriteria criteria, int page, int pageSize, String order, String sortField) {
        CompteChargeSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CompteChargeCriteria criteria) {
        CompteChargeSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public boolean deleteById(Long id) {
        if (id == null) return false;

        CompteCharge compteCharge = findById(id);
        if (compteCharge == null) return false;

        // Sauvegarde temporaire du solde avant suppression
        BigDecimal soldeToDeduct = compteCharge.getSolde();

        deleteAssociatedLists(id);
        dao.deleteById(id);

        // Nettoyage supplémentaire après suppression
        entityManager.flush();
        entityManager.clear();

        return true;
    }

    public void deleteAssociatedLists(Long id) {
        // 1. Trouver tous les Compte liés à ce CompteCharge
        List<Compte> comptesAssocies = compteAdminService.findByCompteChargeId(id);

        // 2. Supprimer la référence CompteCharge dans chaque Compte
        for (Compte compte : comptesAssocies) {
            compte.setCompteCharge(null); // Rompre le lien
            compteAdminService.update(compte); // Sauvegarder le changement
        }

        // 3. Supprimer les charges associées
        chargeService.deleteByCompteChargeId(id);

        // 4. Supprimer les associations TypeCharge
        entityManager.createNativeQuery(
                        "DELETE FROM type_charge_compte_charge WHERE compte_charge = ?")
                .setParameter(1, id)
                .executeUpdate();

        // 5. Mettre à jour le solde global "CHARGE" si nécessaire
        CompteCharge compteCharge = findById(id);
        CompteCharge chargeGlobal = findCharge();
        if (chargeGlobal != null && !chargeGlobal.getId().equals(compteCharge.getId())) {
            chargeGlobal.setSolde(chargeGlobal.getSolde().subtract(compteCharge.getSolde()));
            update(chargeGlobal);
        }
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CompteCharge> delete(List<CompteCharge> list) {
		List<CompteCharge> result = new ArrayList();
        if (list != null) {
            for (CompteCharge t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }

    @Transactional
    public TypeCharge saveOrUpdateTypeCharge(TypeCharge typeCharge) {
        // Correctly handles both new and detached entities
        return typeChargeAdminService.findOrSave(typeCharge);
    }

    /**
     * Crée un nouveau CompteCharge avec ses associations
     * @param t le CompteCharge à créer
     * @return le CompteCharge créé
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public CompteCharge create(CompteCharge t) {
        t.setCode(RandomStringUtils.randomAlphanumeric(5));
        CompteCharge loaded = findByReferenceEntity(t);
        if (loaded != null) {
            return null;
        }
        if(t.getTypeCharges()!=null ){
            TypeCharge typeCharge1 = saveOrUpdateTypeCharge(t.getTypeCharges().iterator().next());
            Set<TypeCharge> initialTypeCharges = new HashSet<>();
            initialTypeCharges.add(typeCharge1);
            t.setTypeCharges(initialTypeCharges);
        }

        // 1. Sauvegarde initiale du CompteCharge
        CompteCharge saved = dao.save(t);

        // 2. Traitement spécial pour code "CHARGE"
        createCompte(saved);

        // 3. Gestion des charges associées
        if (t.getCharges() != null) {
            for (Charge charge : t.getCharges()) {
                charge.setCompteCharge(saved);
                chargeService.create(charge);
            }
        }

        // 4. Gestion des TypeCharges - PROBLÈME RÉSOLU ICI
        if (ListUtil.isNotEmpty(t.getTypeCharges().stream().toList())) {
            Set<TypeCharge> typeChargesToAssociate = new HashSet<>();

            for (TypeCharge typeCharge : t.getTypeCharges()) {
                // 4.1. Sauvegarde ou récupération du TypeCharge
                TypeCharge existingTypeCharge = typeChargeAdminService.findByCode(typeCharge.getCode());
                TypeCharge persistedTypeCharge;


                persistedTypeCharge = existingTypeCharge;


                // Ajout à la collection pour association ultérieure
                typeChargesToAssociate.add(persistedTypeCharge);
            }

            // 4.2. Vider la collection actuelle pour éviter les doublons
            Set<TypeCharge> typeCharges = saved.getTypeCharges();
            if (typeCharges != null) {
                typeCharges.clear();  // Now works because the set is mutable
            }

            // 4.3. Créer manuellement les associations dans la table de jointure
            for (TypeCharge typeCharge : typeChargesToAssociate) {
                // Utilisation d'une requête native pour insérer dans la table d'association
                entityManager.createNativeQuery(
                                "INSERT INTO type_charge_compte_charge (type_charge, compte_charge) VALUES (?, ?)")
                        .setParameter(1, typeCharge.getId())
                        .setParameter(2, saved.getId())
                        .executeUpdate();

                // Ajouter à la collection en mémoire pour cohérence
                saved.getTypeCharges().add(typeCharge);
            }

            CompteCharge byCode = findCharge();
            if (byCode != null) {
                byCode.setSolde(byCode.getSolde().add(saved.getSolde()));
                update(byCode);
            }
            // 4.4. Récupérer l'entité fraîche depuis la base de données pour s'assurer que tout est synchronisé
            entityManager.flush();
            entityManager.refresh(saved);
        }

        return saved;
    }


    public void createCompte(CompteCharge t) {
        Compte compte = new Compte();
        compte.setCompteCharge(t);
        compte.setCode(t.getNom());
        compte.setDateCreation(t.getDateCreation());
        BigDecimal solde = t.getSolde();
        compte.initialize(solde);
        Compte saved = compteAdminService.create(compte);
    }

    public CompteCharge findWithAssociatedLists(Long id){
        CompteCharge result = dao.findById(id).orElse(null);
        if(result!=null && result.getId() != null) {
            result.setCharges(chargeService.findByCompteChargeId(id));

        }
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CompteCharge> update(List<CompteCharge> ts, boolean createIfNotExist) {
        List<CompteCharge> result = new ArrayList<>();
        if (ts != null) {
            for (CompteCharge t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    CompteCharge loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, CompteCharge t, CompteCharge loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }

    public void updateWithAssociatedLists(CompteCharge compteCharge){
    if(compteCharge !=null && compteCharge.getId() != null){
        List<List<Charge>> resultCharges= chargeService.getToBeSavedAndToBeDeleted(chargeService.findByCompteChargeId(compteCharge.getId()),compteCharge.getCharges());
            chargeService.delete(resultCharges.get(1));
        emptyIfNull(resultCharges.get(0)).forEach(e -> e.setCompteCharge(compteCharge));
        chargeService.update(resultCharges.get(0),true);
        }
    }








    public CompteCharge findByReferenceEntity(CompteCharge t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<CompteCharge> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<CompteCharge>> getToBeSavedAndToBeDeleted(List<CompteCharge> oldList, List<CompteCharge> newList) {
        List<List<CompteCharge>> result = new ArrayList<>();
        List<CompteCharge> resultDelete = new ArrayList<>();
        List<CompteCharge> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<CompteCharge> oldList, List<CompteCharge> newList, List<CompteCharge> resultUpdateOrSave, List<CompteCharge> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                CompteCharge myOld = oldList.get(i);
                CompteCharge t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                CompteCharge myNew = newList.get(i);
                CompteCharge t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private ChargeAdminService chargeService ;

    public CompteChargeAdminServiceImpl(CompteAdminService compteAdminService, CompteChargeDao dao) {
        this.compteAdminService = compteAdminService;
        this.dao = dao;
    }

    private CompteChargeDao dao;
@Override
    public CompteCharge findCharge() {
        return dao.findByCode("CHARGE");
    }

    @Override
    public CompteCharge findByCode(String code) {
        return dao.findByCode(code);
    }
}
