package ma.zyn.app.dao.criteria.core.locataire;


import ma.zyn.app.dao.criteria.core.finance.CompteCriteria;
import ma.zyn.app.dao.criteria.core.finance.ModePaiementCriteria;
import ma.zyn.app.dao.criteria.core.finance.TypePaiementCriteria;
import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionCriteria extends  BaseCriteria  {

    private LocalDateTime date;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String montant;
    private String montantMin;
    private String montantMax;
    private String description;
    private String descriptionLike;

    private TypeTransactiontCriteria typeTransaction ;
    private List<TypeTransactiontCriteria> typeTransactions ;
    private ModePaiementCriteria modePaiement ;
    private List<ModePaiementCriteria> modePaiements ;
    private TypePaiementCriteria typePaiement ;
    private List<TypePaiementCriteria> typePaiements ;
    private CompteCriteria compteSource ;
    private List<CompteCriteria> compteSources ;
    private CompteCriteria compteDestination ;
    private List<CompteCriteria> compteDestinations ;


    public LocalDateTime getDate(){
        return this.date;
    }
    public void setDate(LocalDateTime date){
        this.date = date;
    }
    public LocalDateTime getDateFrom(){
        return this.dateFrom;
    }
    public void setDateFrom(LocalDateTime dateFrom){
        this.dateFrom = dateFrom;
    }
    public LocalDateTime getDateTo(){
        return this.dateTo;
    }
    public void setDateTo(LocalDateTime dateTo){
        this.dateTo = dateTo;
    }
    public String getMontant(){
        return this.montant;
    }
    public void setMontant(String montant){
        this.montant = montant;
    }   
    public String getMontantMin(){
        return this.montantMin;
    }
    public void setMontantMin(String montantMin){
        this.montantMin = montantMin;
    }
    public String getMontantMax(){
        return this.montantMax;
    }
    public void setMontantMax(String montantMax){
        this.montantMax = montantMax;
    }
      
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescriptionLike(){
        return this.descriptionLike;
    }
    public void setDescriptionLike(String descriptionLike){
        this.descriptionLike = descriptionLike;
    }


    public TypeTransactiontCriteria getTypeTransaction(){
        return this.typeTransaction;
    }

    public void setTypeTransaction(TypeTransactiontCriteria typeTransaction){
        this.typeTransaction = typeTransaction;
    }
    public List<TypeTransactiontCriteria> getTypeTransactions(){
        return this.typeTransactions;
    }

    public void setTypeTransactions(List<TypeTransactiontCriteria> typeTransactions){
        this.typeTransactions = typeTransactions;
    }
    public ModePaiementCriteria getModePaiement(){
        return this.modePaiement;
    }

    public void setModePaiement(ModePaiementCriteria modePaiement){
        this.modePaiement = modePaiement;
    }
    public List<ModePaiementCriteria> getModePaiements(){
        return this.modePaiements;
    }

    public void setModePaiements(List<ModePaiementCriteria> modePaiements){
        this.modePaiements = modePaiements;
    }
    public TypePaiementCriteria getTypePaiement(){
        return this.typePaiement;
    }

    public void setTypePaiement(TypePaiementCriteria typePaiement){
        this.typePaiement = typePaiement;
    }
    public List<TypePaiementCriteria> getTypePaiements(){
        return this.typePaiements;
    }

    public void setTypePaiements(List<TypePaiementCriteria> typePaiements){
        this.typePaiements = typePaiements;
    }
    public CompteCriteria getCompteSource(){
        return this.compteSource;
    }

    public void setCompteSource(CompteCriteria compteSource){
        this.compteSource = compteSource;
    }
    public List<CompteCriteria> getCompteSources(){
        return this.compteSources;
    }

    public void setCompteSources(List<CompteCriteria> compteSources){
        this.compteSources = compteSources;
    }
    public CompteCriteria getCompteDestination(){
        return this.compteDestination;
    }

    public void setCompteDestination(CompteCriteria compteDestination){
        this.compteDestination = compteDestination;
    }
    public List<CompteCriteria> getCompteDestinations(){
        return this.compteDestinations;
    }

    public void setCompteDestinations(List<CompteCriteria> compteDestinations){
        this.compteDestinations = compteDestinations;
    }
}
