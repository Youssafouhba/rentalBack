package ma.zyn.app.dao.specification.core.locataire;

import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.dao.criteria.core.locataire.LocationCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class LocationSpecification extends  AbstractSpecification<LocationCriteria, Location>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("dateCreation", criteria.getDateCreation(), criteria.getDateCreationFrom(), criteria.getDateCreationTo());
        addPredicate("dateDebut", criteria.getDateDebut(), criteria.getDateDebutFrom(), criteria.getDateDebutTo());
        addPredicate("dateFin", criteria.getDateFin(), criteria.getDateFinFrom(), criteria.getDateFinTo());
        addPredicateBigDecimal("loyer", criteria.getLoyer(), criteria.getLoyerMin(), criteria.getLoyerMax());
        addPredicateBigDecimal("caution", criteria.getCaution(), criteria.getCautionMin(), criteria.getCautionMax());
        addPredicateFk("locataire","id", criteria.getLocataire()==null?null:criteria.getLocataire().getId());
        addPredicateFk("locataire","id", criteria.getLocataires());
        addPredicateFk("locataire","code", criteria.getLocataire()==null?null:criteria.getLocataire().getCode());
        addPredicateFk("local","id", criteria.getLocal()==null?null:criteria.getLocal().getId());
        addPredicateFk("local","id", criteria.getLocals());
        addPredicateFk("local","code", criteria.getLocal()==null?null:criteria.getLocal().getCode());
    }

    public LocationSpecification(LocationCriteria criteria) {
        super(criteria);
    }

    public LocationSpecification(LocationCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
