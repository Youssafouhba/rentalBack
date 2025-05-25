package ma.zyn.app.dao.specification.core.locataire;

import ma.zyn.app.bean.core.locataire.Avoir;
import ma.zyn.app.dao.criteria.core.locataire.AvoirCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class AvoirSpecification extends  AbstractSpecification<AvoirCriteria, Avoir>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateBigDecimal("montant", criteria.getMontant(), criteria.getMontantMin(), criteria.getMontantMax());
        addPredicate("date", criteria.getDate(), criteria.getDateFrom(), criteria.getDateTo());
        addPredicate("motif", criteria.getMotif(),criteria.getMotifLike());
        addPredicateFk("location","id", criteria.getLocation()==null?null:criteria.getLocation().getId());
        addPredicateFk("location","id", criteria.getLocations());
        addPredicateFk("location","code", criteria.getLocation()==null?null:criteria.getLocation().getCode());
    }

    public AvoirSpecification(AvoirCriteria criteria) {
        super(criteria);
    }

    public AvoirSpecification(AvoirCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
