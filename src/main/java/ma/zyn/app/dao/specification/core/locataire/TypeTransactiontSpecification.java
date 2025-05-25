package ma.zyn.app.dao.specification.core.locataire;

import ma.zyn.app.bean.core.locataire.TypeTransactiont;
import ma.zyn.app.dao.criteria.core.locataire.TypeTransactiontCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class TypeTransactiontSpecification extends  AbstractSpecification<TypeTransactiontCriteria, TypeTransactiont>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
    }

    public TypeTransactiontSpecification(TypeTransactiontCriteria criteria) {
        super(criteria);
    }

    public TypeTransactiontSpecification(TypeTransactiontCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
