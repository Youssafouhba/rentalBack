package ma.zyn.app.dao.criteria.core.finance;


import ma.zyn.app.dao.criteria.core.locataire.LocataireCriteria;
import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.util.List;

public class CompteLocataireCriteria extends  BaseCriteria  {

    private String solde;
    private String soldeMin;
    private String soldeMax;
    private String debit;
    private String debitMin;
    private String debitMax;
    private String credit;
    private String creditMin;
    private String creditMax;

    private LocataireCriteria locataire ;
    private List<LocataireCriteria> locataires ;


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


    public LocataireCriteria getLocataire(){
        return this.locataire;
    }

    public void setLocataire(LocataireCriteria locataire){
        this.locataire = locataire;
    }
    public List<LocataireCriteria> getLocataires(){
        return this.locataires;
    }

    public void setLocataires(List<LocataireCriteria> locataires){
        this.locataires = locataires;
    }
}
