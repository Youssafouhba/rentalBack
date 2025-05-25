package ma.zyn.app.dao.criteria.core.finance;


import ma.zyn.app.dao.criteria.core.locaux.LocalCriteria;
import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.time.LocalDateTime;
import java.util.List;

public class ChargeCriteria extends BaseCriteria {

    private String code;
    private String codeLike;
    private String label;
    private String labelLike;
    private String montant;
    private String montantMin;
    private String montantMax;
    private LocalDateTime date;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Boolean isPaid;
    private String description;
    private String descriptionLike;

    private TypeChargeCriteria typeCharge ;
    private List<TypeChargeCriteria> typeCharges ;
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
    public Boolean getIsPaid(){
        return this.isPaid;
    }
    public void setIsPaid(Boolean isPaid){
        this.isPaid = isPaid;
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


    public TypeChargeCriteria getTypeCharge(){
        return this.typeCharge;
    }

    public void setTypeCharge(TypeChargeCriteria typeCharge){
        this.typeCharge = typeCharge;
    }
    public List<TypeChargeCriteria> getTypeCharges(){
        return this.typeCharges;
    }

    public void setTypeCharges(List<TypeChargeCriteria> typeCharges){
        this.typeCharges = typeCharges;
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
