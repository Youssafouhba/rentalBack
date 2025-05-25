package ma.zyn.app.ws.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.bean.core.finance.TypeCharge;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.ws.dto.locaux.LocalDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompteChargeDto  extends AuditBaseDto {

    private String code  ;
    private String label  ;
    private String nom  ;
    private LocalDto local;
    private BigDecimal solde  ;
    private Set<TypeChargeDto> typeCharges = new HashSet<>();
    private String description;
    private String dateCreation;

    private List<ChargeDto> charges ;


    public CompteChargeDto(){
        super();
    }




    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }


    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label = label;
    }


    public String getNom(){
        return this.nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }


    public BigDecimal getSolde(){
        return this.solde;
    }
    public void setSolde(BigDecimal solde){
        this.solde = solde;
    }





    public List<ChargeDto> getCharges(){
        return this.charges;
    }

    public void setCharges(List<ChargeDto> charges){
        this.charges = charges;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public LocalDto getLocal() {
        return local;
    }

    public void setLocal(LocalDto local) {
        this.local = local;
    }

    public Set<TypeChargeDto> getTypeCharges() {
        return typeCharges;
    }

    public void setTypeCharges(Set<TypeChargeDto> typeCharges) {
        this.typeCharges = typeCharges;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
}
