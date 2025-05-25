package  ma.zyn.app.dao.specification.core.locataire;

import ma.zyn.app.dao.criteria.core.locataire.StatutLocalCriteria;
import ma.zyn.app.bean.core.locataire.StatutLocal;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class StatutLocalSpecification extends  AbstractSpecification<StatutLocalCriteria, StatutLocal>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateInt("indexation", criteria.getIndexation(), criteria.getIndexationMin(), criteria.getIndexationMax());
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
        addPredicate("color", criteria.getColor(),criteria.getColorLike());
    }

    public StatutLocalSpecification(StatutLocalCriteria criteria) {
        super(criteria);
    }

    public StatutLocalSpecification(StatutLocalCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
