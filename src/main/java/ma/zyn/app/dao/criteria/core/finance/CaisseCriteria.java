package  ma.zyn.app.dao.criteria.core.finance;



import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.util.List;

public class CaisseCriteria extends  BaseCriteria  {

    private String code;
    private String codeLike;
    private String libelle;
    private String libelleLike;
    private String solde;
    private String soldeMin;
    private String soldeMax;

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateCreationMin() {
        return dateCreationMin;
    }

    public void setDateCreationMin(String dateCreationMin) {
        this.dateCreationMin = dateCreationMin;
    }

    public String getDateCreationMax() {
        return dateCreationMax;
    }

    public void setDateCreationMax(String dateCreationMax) {
        this.dateCreationMax = dateCreationMax;
    }

    public String dateCreation;
    public String dateCreationMin;
    public String dateCreationMax;



    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCodeLike(){
        return this.codeLike;
    }
    public void setCodeLike(String codeLike){
        this.codeLike = codeLike;
    }

    public String getLibelle(){
        return this.libelle;
    }
    public void setLibelle(String libelle){
        this.libelle = libelle;
    }
    public String getLibelleLike(){
        return this.libelleLike;
    }
    public void setLibelleLike(String libelleLike){
        this.libelleLike = libelleLike;
    }

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
      

}
