package ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.dao.criteria.core.finance.CompteLocataireCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CompteLocataireSpecification extends  AbstractSpecification<CompteLocataireCriteria, CompteLocataire>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateBigDecimal("solde", criteria.getSolde(), criteria.getSoldeMin(), criteria.getSoldeMax());
        addPredicateBigDecimal("debit", criteria.getDebit(), criteria.getDebitMin(), criteria.getDebitMax());
        addPredicateBigDecimal("credit", criteria.getCredit(), criteria.getCreditMin(), criteria.getCreditMax());
        addPredicateFk("locataire","id", criteria.getLocataire()==null?null:criteria.getLocataire().getId());
        addPredicateFk("locataire","id", criteria.getLocataires());
        addPredicateFk("locataire","code", criteria.getLocataire()==null?null:criteria.getLocataire().getCode());
    }

    public CompteLocataireSpecification(CompteLocataireCriteria criteria) {
        super(criteria);
    }

    public CompteLocataireSpecification(CompteLocataireCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
