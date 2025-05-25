package ma.zyn.app.service.impl.admin.locataire;


import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locataire.Avoir;
import ma.zyn.app.bean.core.locataire.Reglement;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.criteria.core.locataire.AvoirCriteria;
import ma.zyn.app.dao.facade.core.locataire.AvoirDao;
import ma.zyn.app.dao.specification.core.locataire.AvoirSpecification;
import ma.zyn.app.service.facade.admin.finance.CompteAdminService;
import ma.zyn.app.service.facade.admin.finance.CompteLocataireAdminService;
import ma.zyn.app.service.facade.admin.finance.ModePaiementAdminService;
import ma.zyn.app.service.facade.admin.finance.TypePaiementAdminService;
import ma.zyn.app.service.facade.admin.locataire.AvoirAdminService;
import ma.zyn.app.service.facade.admin.locataire.LocationAdminService;
import ma.zyn.app.service.facade.admin.locataire.TransactionAdminService;
import ma.zyn.app.service.facade.admin.locataire.TypeTransactiontAdminService;
import ma.zyn.app.service.impl.admin.finance.ModePaiementAdminServiceImpl;
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

import static ma.zyn.app.zynerator.util.ListUtil.isEmpty;
import static ma.zyn.app.zynerator.util.ListUtil.isNotEmpty;

@Service
public class AvoirAdminServiceImpl implements AvoirAdminService {
    @Autowired
    private TransactionAdminService transactionAdminService;
    @Autowired private TypeTransactiontAdminService typeTransactiontAdminService;
    @Autowired
    private TypePaiementAdminService typePaiementAdminService;
    @Autowired
    private CompteLocataireAdminService compteLocataireAdminService;
    @Autowired
    private CompteAdminService compteAdminService;
    @Autowired
    private ModePaiementAdminService modePaiementAdminService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Avoir update(Avoir t) {
        Avoir loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Avoir.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Avoir findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Avoir findOrSave(Avoir t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Avoir result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Avoir> findAll() {
        return dao.findAll();
    }

    public List<Avoir> findByCriteria(AvoirCriteria criteria) {
        List<Avoir> content = null;
        if (criteria != null) {
            AvoirSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private AvoirSpecification constructSpecification(AvoirCriteria criteria) {
        AvoirSpecification mySpecification =  (AvoirSpecification) RefelexivityUtil.constructObjectUsingOneParam(AvoirSpecification.class, criteria);
        return mySpecification;
    }

    public List<Avoir> findPaginatedByCriteria(AvoirCriteria criteria, int page, int pageSize, String order, String sortField) {
        AvoirSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(AvoirCriteria criteria) {
        AvoirSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Avoir> findByLocationId(Long id){
        return dao.findByLocationId(id);
    }
    public int deleteByLocationId(Long id){
        return dao.deleteByLocationId(id);
    }
    public long countByLocationCode(String code){
        return dao.countByLocationCode(code);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public boolean deleteById(Long id) {
        if (id == null) return false;

        // 1. Trouver la Charge et son CompteCharge associé
        Avoir avoir = findById(id);

        Transaction transaction = transactionAdminService.findByCode(avoir.getCode());

        Compte source = transaction.getCompteSource();
        Compte destination = transaction.getCompteDestination();

        BigDecimal montant = transaction.getMontant();
        transaction.setCompteSource(null);
        transaction.setCompteDestination(null);
        transaction.setCompteLocataire(null);
        transactionAdminService.deleteById(transaction.getId());
        if(avoir.getLocation()!=null){
            CompteLocataire compteLocataire = compteLocataireAdminService.findByLocataireId(avoir.getLocation().getLocataire().getId());
            if(compteLocataire!=null){
                compteLocataire.subtractCredit(montant); // Annule le débit initial
                compteLocataireAdminService.update(compteLocataire); // Sauvegarde les modifications
            }
        }

        // Réajuster le solde du compte source
        if(source!=null){
            source.subtractDebit(montant); // Annule le débit initial
            source.recalcSolde();
            compteAdminService.update(source); // Sauvegarde les modifications
        }
        if(destination!=null){
            destination.subtractCredit(montant); // Annule le crédit initial
            destination.recalcSolde();
            compteAdminService.update(destination);
        }


        // 5. Supprimer la Charge elle-même
        dao.deleteById(id);

        return true;
    }






    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Avoir> delete(List<Avoir> list) {
        List<Avoir> result = new ArrayList();
        if (list != null) {
            for (Avoir t : list) {
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
    public Avoir create(Avoir t) {
        Avoir loaded = findByReferenceEntity(t);
        Avoir saved;
        if (loaded == null) {
            t.setLocation(locationService.findByReferenceEntity(t.getLocation()));
            saved = dao.save(t);
            createTransaction(saved);
        }else {
            saved = null;
        }
        return saved;
    }


    public void createTransaction(Avoir avoir) {
        // Validation du règlement
        if (avoir == null || avoir.getMontant() == null || avoir.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Règlement invalide.");
        }
        Transaction transaction = new Transaction();
        transaction.setCode(avoir.getCode());
        transaction.setDate(avoir.getDate());
        transaction.setMontant(avoir.getMontant());
        transaction.setDescription(avoir.getMotif());
        // Récupération ou sauvegarde des objets
        transaction.setTypeTransaction(typeTransactiontAdminService.findAvoir());
        transaction.setTypePaiement(typePaiementAdminService.findDebit());
        transaction.setModePaiement(modePaiementAdminService.findByCode(avoir.getModePaiement().getCode()));
        CompteLocataire byLocataireId = compteLocataireAdminService.findByLocataireId(avoir.getLocation().getLocataire().getId());
        transaction.setCompteLocataire(byLocataireId);

        if (avoir.getCompteSource() != null) {
            Compte comteSource = compteAdminService.findByCode(avoir.getCompteSource());
            if (comteSource != null) {
                transaction.setCompteSource(comteSource);
            }
        }

        // Création de la transaction
        transactionAdminService.create(transaction);
    }



    public Avoir findWithAssociatedLists(Long id){
        Avoir result = dao.findById(id).orElse(null);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Avoir> update(List<Avoir> ts, boolean createIfNotExist) {
        List<Avoir> result = new ArrayList<>();
        if (ts != null) {
            for (Avoir t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Avoir loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Avoir t, Avoir loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Avoir findByReferenceEntity(Avoir t) {
        return t == null  ? null : findByCode(t.getCode());
    }

    @Override
    public Avoir findByCode(String code) {
        return dao.findByCode(code);
    }

    public void findOrSaveAssociatedObject(Avoir t){
        if( t != null) {
            t.setLocation(locationService.findOrSave(t.getLocation()));
        }
    }



    public List<Avoir> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<Avoir>> getToBeSavedAndToBeDeleted(List<Avoir> oldList, List<Avoir> newList) {
        List<List<Avoir>> result = new ArrayList<>();
        List<Avoir> resultDelete = new ArrayList<>();
        List<Avoir> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Avoir> oldList, List<Avoir> newList, List<Avoir> resultUpdateOrSave, List<Avoir> resultDelete) {
        for (int i = 0; i < oldList.size(); i++) {
            Avoir myOld = oldList.get(i);
            Avoir t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
            if (t != null) {
                resultUpdateOrSave.add(t); // update
            } else {
                resultDelete.add(myOld);
            }
        }
        for (int i = 0; i < newList.size(); i++) {
            Avoir myNew = newList.get(i);
            Avoir t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    public void deleteByLocataireId(Long id) {
        dao.deleteByLocataireId(id);
    }

    @Override
    public List<Avoir> findByLocataireId(Long id) {
        return dao.findByLocataireId(id);
    }


    @Autowired
    private LocationAdminService locationService ;

    public AvoirAdminServiceImpl(AvoirDao dao) {
        this.dao = dao;
    }

    private AvoirDao dao;
}
