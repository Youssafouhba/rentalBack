package ma.zyn.app.dao.specification.core.locataire;

import ma.zyn.app.bean.core.locataire.Reglement;
import ma.zyn.app.dao.criteria.core.locataire.ReglementCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class ReglementSpecification extends  AbstractSpecification<ReglementCriteria, Reglement>  {

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

    public ReglementSpecification(ReglementCriteria criteria) {
        super(criteria);
    }

    public ReglementSpecification(ReglementCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
