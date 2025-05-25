package ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.bean.core.finance.TypeCharge;
import ma.zyn.app.dao.criteria.core.finance.TypeChargeCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class TypeChargeSpecification extends  AbstractSpecification<TypeChargeCriteria, TypeCharge>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
        addPredicate("description", criteria.getDescription(),criteria.getDescriptionLike());
    }

    public TypeChargeSpecification(TypeChargeCriteria criteria) {
        super(criteria);
    }

    public TypeChargeSpecification(TypeChargeCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
