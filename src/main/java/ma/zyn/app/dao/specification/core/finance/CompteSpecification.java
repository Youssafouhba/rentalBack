package ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.dao.criteria.core.finance.CompteCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CompteSpecification extends  AbstractSpecification<CompteCriteria, Compte>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateBigDecimal("solde", criteria.getSolde(), criteria.getSoldeMin(), criteria.getSoldeMax());
        addPredicateBigDecimal("debit", criteria.getDebit(), criteria.getDebitMin(), criteria.getDebitMax());
        addPredicateBigDecimal("credit", criteria.getCredit(), criteria.getCreditMin(), criteria.getCreditMax());
        addPredicateInt("numeroCompte", criteria.getNumeroCompte(), criteria.getNumeroCompteMin(), criteria.getNumeroCompteMax());
        addPredicate("dateCreation", criteria.getDateCreation(), criteria.getDateCreationFrom(), criteria.getDateCreationTo());
        addPredicateFk("banque","id", criteria.getBanque()==null?null:criteria.getBanque().getId());
        addPredicateFk("banque","id", criteria.getBanques());
        addPredicateFk("banque","code", criteria.getBanque()==null?null:criteria.getBanque().getCode());
    }

    public CompteSpecification(CompteCriteria criteria) {
        super(criteria);
    }

    public CompteSpecification(CompteCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
