package ma.zyn.app.dao.criteria.core.locataire;


import ma.zyn.app.dao.criteria.core.finance.CompteInstantaneeCriteria;
import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.time.LocalDateTime;
import java.util.List;

public class LocataireCriteria extends  BaseCriteria  {

    private String code;
    private String codeLike;
    private String nom;
    private String nomLike;
    private String prenom;
    private String prenomLike;
    private String telephone;
    private String telephoneLike;
    private LocalDateTime dateCreation;
    private LocalDateTime dateCreationFrom;
    private LocalDateTime dateCreationTo;

    private TypeLocataireCriteria typeLocataire ;
    private List<TypeLocataireCriteria> typeLocataires ;
    private CompteInstantaneeCriteria CompteInstantanee ;
    private List<CompteInstantaneeCriteria> CompteInstantanees ;


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

    public String getPrenom(){
        return this.prenom;
    }
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    public String getPrenomLike(){
        return this.prenomLike;
    }
    public void setPrenomLike(String prenomLike){
        this.prenomLike = prenomLike;
    }

    public String getTelephone(){
        return this.telephone;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public String getTelephoneLike(){
        return this.telephoneLike;
    }
    public void setTelephoneLike(String telephoneLike){
        this.telephoneLike = telephoneLike;
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

    public TypeLocataireCriteria getTypeLocataire(){
        return this.typeLocataire;
    }

    public void setTypeLocataire(TypeLocataireCriteria typeLocataire){
        this.typeLocataire = typeLocataire;
    }
    public List<TypeLocataireCriteria> getTypeLocataires(){
        return this.typeLocataires;
    }

    public void setTypeLocataires(List<TypeLocataireCriteria> typeLocataires){
        this.typeLocataires = typeLocataires;
    }
    public CompteInstantaneeCriteria getCompteInstantanee(){
        return this.CompteInstantanee;
    }

    public void setCompteInstantanee(CompteInstantaneeCriteria CompteInstantanee){
        this.CompteInstantanee = CompteInstantanee;
    }
    public List<CompteInstantaneeCriteria> getCompteInstantanees(){
        return this.CompteInstantanees;
    }

    public void setCompteInstantanees(List<CompteInstantaneeCriteria> CompteInstantanees){
        this.CompteInstantanees = CompteInstantanees;
    }
}
