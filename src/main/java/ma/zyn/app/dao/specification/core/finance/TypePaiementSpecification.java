package ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.bean.core.finance.TypePaiement;
import ma.zyn.app.dao.criteria.core.finance.TypePaiementCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class TypePaiementSpecification extends  AbstractSpecification<TypePaiementCriteria, TypePaiement>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateInt("indexation", criteria.getIndexation(), criteria.getIndexationMin(), criteria.getIndexationMax());
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
    }

    public TypePaiementSpecification(TypePaiementCriteria criteria) {
        super(criteria);
    }

    public TypePaiementSpecification(TypePaiementCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
