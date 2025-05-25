package ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.bean.core.finance.CompteAdmin;
import ma.zyn.app.dao.criteria.core.finance.CompteAdminCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CompteAdminSpecification extends  AbstractSpecification<CompteAdminCriteria, CompteAdmin>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateBigDecimal("solde", criteria.getSolde(), criteria.getSoldeMin(), criteria.getSoldeMax());
        addPredicate("dateCreation", criteria.getDateCreation(), criteria.getDateCreationFrom(), criteria.getDateCreationTo());
    }

    public CompteAdminSpecification(CompteAdminCriteria criteria) {
        super(criteria);
    }

    public CompteAdminSpecification(CompteAdminCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
