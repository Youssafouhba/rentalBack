package ma.zyn.app.dao.specification.core.locataire;

import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.dao.criteria.core.locataire.LocataireCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class LocataireSpecification extends  AbstractSpecification<LocataireCriteria, Locataire>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("nom", criteria.getNom(),criteria.getNomLike());
        addPredicate("prenom", criteria.getPrenom(),criteria.getPrenomLike());
        addPredicate("telephone", criteria.getTelephone(),criteria.getTelephoneLike());
        addPredicate("dateCreation", criteria.getDateCreation(), criteria.getDateCreationFrom(), criteria.getDateCreationTo());
        addPredicateFk("typeLocataire","id", criteria.getTypeLocataire()==null?null:criteria.getTypeLocataire().getId());
        addPredicateFk("typeLocataire","id", criteria.getTypeLocataires());
        addPredicateFk("typeLocataire","code", criteria.getTypeLocataire()==null?null:criteria.getTypeLocataire().getCode());
        addPredicateFk("CompteInstantanee","id", criteria.getCompteInstantanee()==null?null:criteria.getCompteInstantanee().getId());
        addPredicateFk("CompteInstantanee","id", criteria.getCompteInstantanees());
    }

    public LocataireSpecification(LocataireCriteria criteria) {
        super(criteria);
    }

    public LocataireSpecification(LocataireCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
