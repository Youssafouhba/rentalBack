package ma.zyn.app.dao.specification.core.locaux;

import ma.zyn.app.bean.core.locaux.TypeLocal;
import ma.zyn.app.dao.criteria.core.locaux.TypeLocalCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class TypeLocalSpecification extends  AbstractSpecification<TypeLocalCriteria, TypeLocal>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
    }

    public TypeLocalSpecification(TypeLocalCriteria criteria) {
        super(criteria);
    }

    public TypeLocalSpecification(TypeLocalCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
