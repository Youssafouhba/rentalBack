package  ma.zyn.app.dao.specification.core.finance;

import ma.zyn.app.dao.criteria.core.finance.ChargeCriteria;
import ma.zyn.app.bean.core.finance.Charge;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class ChargeSpecification extends  AbstractSpecification<ChargeCriteria, Charge>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicateBigDecimal("montant", criteria.getMontant(), criteria.getMontantMin(), criteria.getMontantMax());
        addPredicate("date", criteria.getDate(), criteria.getDateFrom(), criteria.getDateTo());
        addPredicateBool("isPaid", criteria.getIsPaid());
        addPredicate("description", criteria.getDescription(),criteria.getDescriptionLike());
        addPredicateFk("typeCharge","id", criteria.getTypeCharge()==null?null:criteria.getTypeCharge().getId());
        addPredicateFk("typeCharge","id", criteria.getTypeCharges());
        addPredicateFk("typeCharge","code", criteria.getTypeCharge()==null?null:criteria.getTypeCharge().getCode());
        addPredicateFk("local","id", criteria.getLocal()==null?null:criteria.getLocal().getId());
        addPredicateFk("local","id", criteria.getLocals());
        addPredicateFk("local","code", criteria.getLocal()==null?null:criteria.getLocal().getCode());
    }

    public ChargeSpecification(ChargeCriteria criteria) {
        super(criteria);
    }

    public ChargeSpecification(ChargeCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
