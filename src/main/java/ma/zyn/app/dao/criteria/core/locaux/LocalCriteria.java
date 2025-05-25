package ma.zyn.app.dao.criteria.core.locaux;


import ma.zyn.app.dao.criteria.core.locataire.StatutLocalCriteria;
import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.time.LocalDateTime;
import java.util.List;

public class LocalCriteria extends  BaseCriteria  {

    private String code;
    private String codeLike;
    private String adresse;
    private String adresseLike;
    private LocalDateTime dateCreation;
    private LocalDateTime dateCreationFrom;
    private LocalDateTime dateCreationTo;
    private String label;
    private String labelLike;
    private String superficie;
    private String superficieMin;
    private String superficieMax;
    private String prix;
    private String prixMin;
    private String prixMax;
    private String description;
    private String descriptionLike;
    private String montantMensuel;
    private String montantMensuelMin;
    private String montantMensuelMax;

    private StatutLocalCriteria statutLocal ;
    private List<StatutLocalCriteria> statutLocals ;
    private TypeLocalCriteria typeLocal ;
    private List<TypeLocalCriteria> typeLocals ;


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

    public String getAdresse(){
        return this.adresse;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }
    public String getAdresseLike(){
        return this.adresseLike;
    }
    public void setAdresseLike(String adresseLike){
        this.adresseLike = adresseLike;
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

    public String getSuperficie(){
        return this.superficie;
    }
    public void setSuperficie(String superficie){
        this.superficie = superficie;
    }   
    public String getSuperficieMin(){
        return this.superficieMin;
    }
    public void setSuperficieMin(String superficieMin){
        this.superficieMin = superficieMin;
    }
    public String getSuperficieMax(){
        return this.superficieMax;
    }
    public void setSuperficieMax(String superficieMax){
        this.superficieMax = superficieMax;
    }
      
    public String getPrix(){
        return this.prix;
    }
    public void setPrix(String prix){
        this.prix = prix;
    }   
    public String getPrixMin(){
        return this.prixMin;
    }
    public void setPrixMin(String prixMin){
        this.prixMin = prixMin;
    }
    public String getPrixMax(){
        return this.prixMax;
    }
    public void setPrixMax(String prixMax){
        this.prixMax = prixMax;
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

    public String getMontantMensuel(){
        return this.montantMensuel;
    }
    public void setMontantMensuel(String montantMensuel){
        this.montantMensuel = montantMensuel;
    }   
    public String getMontantMensuelMin(){
        return this.montantMensuelMin;
    }
    public void setMontantMensuelMin(String montantMensuelMin){
        this.montantMensuelMin = montantMensuelMin;
    }
    public String getMontantMensuelMax(){
        return this.montantMensuelMax;
    }
    public void setMontantMensuelMax(String montantMensuelMax){
        this.montantMensuelMax = montantMensuelMax;
    }
      

    public StatutLocalCriteria getStatutLocal(){
        return this.statutLocal;
    }

    public void setStatutLocal(StatutLocalCriteria statutLocal){
        this.statutLocal = statutLocal;
    }
    public List<StatutLocalCriteria> getStatutLocals(){
        return this.statutLocals;
    }

    public void setStatutLocals(List<StatutLocalCriteria> statutLocals){
        this.statutLocals = statutLocals;
    }
    public TypeLocalCriteria getTypeLocal(){
        return this.typeLocal;
    }

    public void setTypeLocal(TypeLocalCriteria typeLocal){
        this.typeLocal = typeLocal;
    }
    public List<TypeLocalCriteria> getTypeLocals(){
        return this.typeLocals;
    }

    public void setTypeLocals(List<TypeLocalCriteria> typeLocals){
        this.typeLocals = typeLocals;
    }
}
