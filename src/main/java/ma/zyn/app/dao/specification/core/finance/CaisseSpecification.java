package  ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.dao.criteria.core.finance.CaisseCriteria;
import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CaisseSpecification extends  AbstractSpecification<CaisseCriteria, Caisse>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
        addPredicateBigDecimal("solde", criteria.getSolde(), criteria.getSoldeMin(), criteria.getSoldeMax());
    }

    public CaisseSpecification(CaisseCriteria criteria) {
        super(criteria);
    }

    public CaisseSpecification(CaisseCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
