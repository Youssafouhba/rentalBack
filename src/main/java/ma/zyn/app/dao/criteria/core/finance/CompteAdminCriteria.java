package ma.zyn.app.dao.criteria.core.finance;



import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.time.LocalDateTime;

public class CompteAdminCriteria extends  BaseCriteria  {

    private String solde;
    private String soldeMin;
    private String soldeMax;
    private LocalDateTime dateCreation;
    private LocalDateTime dateCreationFrom;
    private LocalDateTime dateCreationTo;



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

}
