package  ma.zyn.app.ws.dto.finance;

import ma.zyn.app.ws.dto.locataire.ModePaiementDto;
import ma.zyn.app.ws.dto.locaux.LocalDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChargeDto  extends AuditBaseDto {

    private ModePaiementDto modePaiement ;
    private String code  ;
    private String label  ;
    private BigDecimal montant  ;
    private String date ;
    private Boolean isPaid  ;
    private String description  ;
    private CompteChargeDto compteCharge;
    private CompteDto compteSource;

    private TypeChargeDto typeCharge ;
    private LocalDto local ;



    public ChargeDto(){
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


    public BigDecimal getMontant(){
        return this.montant;
    }
    public void setMontant(BigDecimal montant){
        this.montant = montant;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
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


    public TypeChargeDto getTypeCharge(){
        return this.typeCharge;
    }

    public void setTypeCharge(TypeChargeDto typeCharge){
        this.typeCharge = typeCharge;
    }
    public LocalDto getLocal(){
        return this.local;
    }

    public void setLocal(LocalDto local){
        this.local = local;
    }


    public CompteChargeDto getCompteCharge() {
        return compteCharge;
    }

    public void setCompteCharge(CompteChargeDto compteCharge) {
        this.compteCharge = compteCharge;
    }

    public CompteDto getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(CompteDto compteSource) {
        this.compteSource = compteSource;
    }

    public ModePaiementDto getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiementDto modePaiement) {
        this.modePaiement = modePaiement;
    }
}
