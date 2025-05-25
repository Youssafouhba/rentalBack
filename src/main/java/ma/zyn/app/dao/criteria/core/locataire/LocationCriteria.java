package ma.zyn.app.dao.criteria.core.locataire;


import ma.zyn.app.dao.criteria.core.locaux.LocalCriteria;
import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.time.LocalDateTime;
import java.util.List;

public class LocationCriteria extends  BaseCriteria  {

    private String code;
    private String codeLike;
    private LocalDateTime dateCreation;
    private LocalDateTime dateCreationFrom;
    private LocalDateTime dateCreationTo;
    private LocalDateTime dateDebut;
    private LocalDateTime dateDebutFrom;
    private LocalDateTime dateDebutTo;
    private LocalDateTime dateFin;
    private LocalDateTime dateFinFrom;
    private LocalDateTime dateFinTo;
    private String loyer;
    private String loyerMin;
    private String loyerMax;
    private String caution;
    private String cautionMin;
    private String cautionMax;

    private LocataireCriteria locataire ;
    private List<LocataireCriteria> locataires ;
    private LocalCriteria local ;
    private List<LocalCriteria> locals ;


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
    public LocalDateTime getDateDebut(){
        return this.dateDebut;
    }
    public void setDateDebut(LocalDateTime dateDebut){
        this.dateDebut = dateDebut;
    }
    public LocalDateTime getDateDebutFrom(){
        return this.dateDebutFrom;
    }
    public void setDateDebutFrom(LocalDateTime dateDebutFrom){
        this.dateDebutFrom = dateDebutFrom;
    }
    public LocalDateTime getDateDebutTo(){
        return this.dateDebutTo;
    }
    public void setDateDebutTo(LocalDateTime dateDebutTo){
        this.dateDebutTo = dateDebutTo;
    }
    public LocalDateTime getDateFin(){
        return this.dateFin;
    }
    public void setDateFin(LocalDateTime dateFin){
        this.dateFin = dateFin;
    }
    public LocalDateTime getDateFinFrom(){
        return this.dateFinFrom;
    }
    public void setDateFinFrom(LocalDateTime dateFinFrom){
        this.dateFinFrom = dateFinFrom;
    }
    public LocalDateTime getDateFinTo(){
        return this.dateFinTo;
    }
    public void setDateFinTo(LocalDateTime dateFinTo){
        this.dateFinTo = dateFinTo;
    }
    public String getLoyer(){
        return this.loyer;
    }
    public void setLoyer(String loyer){
        this.loyer = loyer;
    }   
    public String getLoyerMin(){
        return this.loyerMin;
    }
    public void setLoyerMin(String loyerMin){
        this.loyerMin = loyerMin;
    }
    public String getLoyerMax(){
        return this.loyerMax;
    }
    public void setLoyerMax(String loyerMax){
        this.loyerMax = loyerMax;
    }
      
    public String getCaution(){
        return this.caution;
    }
    public void setCaution(String caution){
        this.caution = caution;
    }   
    public String getCautionMin(){
        return this.cautionMin;
    }
    public void setCautionMin(String cautionMin){
        this.cautionMin = cautionMin;
    }
    public String getCautionMax(){
        return this.cautionMax;
    }
    public void setCautionMax(String cautionMax){
        this.cautionMax = cautionMax;
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
    public LocalCriteria getLocal(){
        return this.local;
    }

    public void setLocal(LocalCriteria local){
        this.local = local;
    }
    public List<LocalCriteria> getLocals(){
        return this.locals;
    }

    public void setLocals(List<LocalCriteria> locals){
        this.locals = locals;
    }
}
