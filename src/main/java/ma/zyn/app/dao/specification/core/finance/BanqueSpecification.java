package  ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.dao.criteria.core.finance.BanqueCriteria;
import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class BanqueSpecification extends  AbstractSpecification<BanqueCriteria, Banque>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
        addPredicateBigDecimal("solde", criteria.getSolde(), criteria.getSoldeMin(), criteria.getSoldeMax());
    }

    public BanqueSpecification(BanqueCriteria criteria) {
        super(criteria);
    }

    public BanqueSpecification(BanqueCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
