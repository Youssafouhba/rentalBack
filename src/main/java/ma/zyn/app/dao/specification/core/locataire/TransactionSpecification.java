package ma.zyn.app.dao.specification.core.locataire;

import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.dao.criteria.core.locataire.TransactionCriteria;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class TransactionSpecification extends  AbstractSpecification<TransactionCriteria, Transaction>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("date", criteria.getDate(), criteria.getDateFrom(), criteria.getDateTo());
        addPredicateBigDecimal("montant", criteria.getMontant(), criteria.getMontantMin(), criteria.getMontantMax());
        addPredicate("description", criteria.getDescription(),criteria.getDescriptionLike());
        addPredicateFk("typeTransaction","id", criteria.getTypeTransaction()==null?null:criteria.getTypeTransaction().getId());
        addPredicateFk("typeTransaction","id", criteria.getTypeTransactions());
        addPredicateFk("typeTransaction","code", criteria.getTypeTransaction()==null?null:criteria.getTypeTransaction().getCode());
        addPredicateFk("modePaiement","id", criteria.getModePaiement()==null?null:criteria.getModePaiement().getId());
        addPredicateFk("modePaiement","id", criteria.getModePaiements());
        addPredicateFk("modePaiement","code", criteria.getModePaiement()==null?null:criteria.getModePaiement().getCode());
        addPredicateFk("typePaiement","id", criteria.getTypePaiement()==null?null:criteria.getTypePaiement().getId());
        addPredicateFk("typePaiement","id", criteria.getTypePaiements());
        addPredicateFk("typePaiement","code", criteria.getTypePaiement()==null?null:criteria.getTypePaiement().getCode());
        addPredicateFk("compteSource","id", criteria.getCompteSource()==null?null:criteria.getCompteSource().getId());
        addPredicateFk("compteSource","id", criteria.getCompteSources());
        addPredicateFk("compteDestination","id", criteria.getCompteDestination()==null?null:criteria.getCompteDestination().getId());
        addPredicateFk("compteDestination","id", criteria.getCompteDestinations());
    }

    public TransactionSpecification(TransactionCriteria criteria) {
        super(criteria);
    }

    public TransactionSpecification(TransactionCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
