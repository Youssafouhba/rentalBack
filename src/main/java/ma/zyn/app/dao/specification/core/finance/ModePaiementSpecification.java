package ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.bean.core.finance.ModePaiement;
import ma.zyn.app.dao.criteria.core.finance.ModePaiementCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class ModePaiementSpecification extends  AbstractSpecification<ModePaiementCriteria, ModePaiement>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateInt("indexation", criteria.getIndexation(), criteria.getIndexationMin(), criteria.getIndexationMax());
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
    }

    public ModePaiementSpecification(ModePaiementCriteria criteria) {
        super(criteria);
    }

    public ModePaiementSpecification(ModePaiementCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
