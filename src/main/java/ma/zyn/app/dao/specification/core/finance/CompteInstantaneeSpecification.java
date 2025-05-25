package ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.dao.criteria.core.finance.CompteInstantaneeCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CompteInstantaneeSpecification extends  AbstractSpecification<CompteInstantaneeCriteria, CompteInstantanee>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateBigDecimal("solde", criteria.getSolde(), criteria.getSoldeMin(), criteria.getSoldeMax());
        addPredicateBigDecimal("debit", criteria.getDebit(), criteria.getDebitMin(), criteria.getDebitMax());
        addPredicateBigDecimal("credit", criteria.getCredit(), criteria.getCreditMin(), criteria.getCreditMax());
        addPredicateFk("locataire","id", criteria.getLocataire()==null?null:criteria.getLocataire().getId());
        addPredicateFk("locataire","id", criteria.getLocataires());
        addPredicateFk("local","code", criteria.getLocataire()==null?null:criteria.getLocal().getCode());
        addPredicateFk("local","id", criteria.getLocataire()==null?null:criteria.getLocal().getId());
        addPredicateFk("locataire","code", criteria.getLocataire()==null?null:criteria.getLocataire().getCode());
    }

    public CompteInstantaneeSpecification(CompteInstantaneeCriteria criteria) {
        super(criteria);
    }

    public CompteInstantaneeSpecification(CompteInstantaneeCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
