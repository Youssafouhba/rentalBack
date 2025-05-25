package ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.dao.criteria.core.finance.CompteChargeCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CompteChargeSpecification extends  AbstractSpecification<CompteChargeCriteria, CompteCharge>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("nom", criteria.getNom(),criteria.getNomLike());
        addPredicateBigDecimal("solde", criteria.getSolde(), criteria.getSoldeMin(), criteria.getSoldeMax());
    }

    public CompteChargeSpecification(CompteChargeCriteria criteria) {
        super(criteria);
    }

    public CompteChargeSpecification(CompteChargeCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
