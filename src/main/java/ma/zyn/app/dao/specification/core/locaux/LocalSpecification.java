package ma.zyn.app.dao.specification.core.locaux;

import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.dao.criteria.core.locaux.LocalCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class LocalSpecification extends  AbstractSpecification<LocalCriteria, Local>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("adresse", criteria.getAdresse(),criteria.getAdresseLike());
        addPredicate("dateCreation", criteria.getDateCreation(), criteria.getDateCreationFrom(), criteria.getDateCreationTo());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("superficie", criteria.getSuperficie(),criteria.getSuperficie());
        addPredicateBigDecimal("prix", criteria.getPrix(), criteria.getPrixMin(), criteria.getPrixMax());
        addPredicateBigDecimal("montantMensuel", criteria.getMontantMensuel(), criteria.getMontantMensuelMin(), criteria.getMontantMensuelMax());
        addPredicateFk("statutLocal","id", criteria.getStatutLocal()==null?null:criteria.getStatutLocal().getId());
        addPredicateFk("statutLocal","id", criteria.getStatutLocals());
        addPredicateFk("statutLocal","code", criteria.getStatutLocal()==null?null:criteria.getStatutLocal().getCode());
        addPredicateFk("typeLocal","id", criteria.getTypeLocal()==null?null:criteria.getTypeLocal().getId());
        addPredicateFk("typeLocal","id", criteria.getTypeLocals());
        addPredicateFk("typeLocal","code", criteria.getTypeLocal()==null?null:criteria.getTypeLocal().getCode());
    }

    public LocalSpecification(LocalCriteria criteria) {
        super(criteria);
    }

    public LocalSpecification(LocalCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
