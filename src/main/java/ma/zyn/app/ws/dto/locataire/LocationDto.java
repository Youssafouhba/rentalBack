package ma.zyn.app.ws.dto.locataire;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import ma.zyn.app.ws.dto.locaux.LocalDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto extends AuditBaseDto {
    private BigDecimal solde = BigDecimal.ZERO;

    private String code  ;
    private String dateCreation ;
    private String dateDebut ;
    private String dateFin ;
    private BigDecimal loyer  ;
    private BigDecimal caution  ;
    private boolean actif;
    private LocataireDto locataire ;
    private LocalDto local ;


    @Column(length = 500)
    private String reference;

    public LocationDto(){
        super();
    }




    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(String dateCreation){
        this.dateCreation = dateCreation;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateDebut(){
        return this.dateDebut;
    }
    public void setDateDebut(String dateDebut){
        this.dateDebut = dateDebut;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateFin(){
        return this.dateFin;
    }
    public void setDateFin(String dateFin){
        this.dateFin = dateFin;
    }


    public BigDecimal getLoyer(){
        return this.loyer;
    }
    public void setLoyer(BigDecimal loyer){
        this.loyer = loyer;
    }


    public BigDecimal getCaution(){
        return this.caution;
    }
    public void setCaution(BigDecimal caution){
        this.caution = caution;
    }


    public LocataireDto getLocataire(){
        return this.locataire;
    }

    public void setLocataire(LocataireDto locataire){
        this.locataire = locataire;
    }
    public LocalDto getLocal(){
        return this.local;
    }

    public void setLocal(LocalDto local){
        this.local = local;
    }


    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }
}
