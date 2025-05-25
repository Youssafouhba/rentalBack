package ma.zyn.app.dao.criteria.core.finance;


import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.time.LocalDateTime;
import java.util.List;

public class CompteCriteria extends  BaseCriteria  {

    private String solde;
    private String soldeMin;
    private String soldeMax;
    private String debit;
    private String debitMin;
    private String debitMax;
    private String credit;
    private String creditMin;
    private String creditMax;
    private String numeroCompte;
    private String numeroCompteMin;
    private String numeroCompteMax;
    private LocalDateTime dateCreation;
    private LocalDateTime dateCreationFrom;
    private LocalDateTime dateCreationTo;

    private BanqueCriteria banque ;
    private List<BanqueCriteria> banques ;


    public String getSolde(){
        return this.solde;
    }
    public void setSolde(String solde){
        this.solde = solde;
    }   
    public String getSoldeMin(){
        return this.soldeMin;
    }
    public void setSoldeMin(String soldeMin){
        this.soldeMin = soldeMin;
    }
    public String getSoldeMax(){
        return this.soldeMax;
    }
    public void setSoldeMax(String soldeMax){
        this.soldeMax = soldeMax;
    }
      
    public String getDebit(){
        return this.debit;
    }
    public void setDebit(String debit){
        this.debit = debit;
    }   
    public String getDebitMin(){
        return this.debitMin;
    }
    public void setDebitMin(String debitMin){
        this.debitMin = debitMin;
    }
    public String getDebitMax(){
        return this.debitMax;
    }
    public void setDebitMax(String debitMax){
        this.debitMax = debitMax;
    }
      
    public String getCredit(){
        return this.credit;
    }
    public void setCredit(String credit){
        this.credit = credit;
    }   
    public String getCreditMin(){
        return this.creditMin;
    }
    public void setCreditMin(String creditMin){
        this.creditMin = creditMin;
    }
    public String getCreditMax(){
        return this.creditMax;
    }
    public void setCreditMax(String creditMax){
        this.creditMax = creditMax;
    }
      
    public String getNumeroCompte(){
        return this.numeroCompte;
    }
    public void setNumeroCompte(String numeroCompte){
        this.numeroCompte = numeroCompte;
    }   
    public String getNumeroCompteMin(){
        return this.numeroCompteMin;
    }
    public void setNumeroCompteMin(String numeroCompteMin){
        this.numeroCompteMin = numeroCompteMin;
    }
    public String getNumeroCompteMax(){
        return this.numeroCompteMax;
    }
    public void setNumeroCompteMax(String numeroCompteMax){
        this.numeroCompteMax = numeroCompteMax;
    }
      
    public LocalDateTime getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(LocalDateTime dateCreation){
        this.dateCreation = dateCreation;
    }
    public LocalDateTime getDateCreationFrom(){
        return this.dateCreationFrom;
    }
    public void setDateCreationFrom(LocalDateTime dateCreationFrom){
        this.dateCreationFrom = dateCreationFrom;
    }
    public LocalDateTime getDateCreationTo(){
        return this.dateCreationTo;
    }
    public void setDateCreationTo(LocalDateTime dateCreationTo){
        this.dateCreationTo = dateCreationTo;
    }

    public BanqueCriteria getBanque(){
        return this.banque;
    }

    public void setBanque(BanqueCriteria banque){
        this.banque = banque;
    }
    public List<BanqueCriteria> getBanques(){
        return this.banques;
    }

    public void setBanques(List<BanqueCriteria> banques){
        this.banques = banques;
    }
}
