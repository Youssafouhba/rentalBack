package  ma.zyn.app.dao.specification.core.locataire;

import ma.zyn.app.dao.criteria.core.locataire.TypeLocataireCriteria;
import ma.zyn.app.bean.core.locataire.TypeLocataire;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class TypeLocataireSpecification extends  AbstractSpecification<TypeLocataireCriteria, TypeLocataire>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateInt("indexation", criteria.getIndexation(), criteria.getIndexationMin(), criteria.getIndexationMax());
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
        addPredicate("color", criteria.getColor(),criteria.getColorLike());
    }

    public TypeLocataireSpecification(TypeLocataireCriteria criteria) {
        super(criteria);
    }

    public TypeLocataireSpecification(TypeLocataireCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
