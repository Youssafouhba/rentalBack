package ma.zyn.app.dao.criteria.core.finance;



import ma.zyn.app.zynerator.criteria.BaseCriteria;

public class CompteChargeCriteria extends  BaseCriteria  {

    private String code;
    private String codeLike;
    private String label;
    private String labelLike;
    private String nom;
    private String nomLike;
    private String solde;
    private String soldeMin;
    private String soldeMax;



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

    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabelLike(){
        return this.labelLike;
    }
    public void setLabelLike(String labelLike){
        this.labelLike = labelLike;
    }

    public String getNom(){
        return this.nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public String getNomLike(){
        return this.nomLike;
    }
    public void setNomLike(String nomLike){
        this.nomLike = nomLike;
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
