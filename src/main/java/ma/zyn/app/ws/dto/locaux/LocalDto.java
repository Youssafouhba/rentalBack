package ma.zyn.app.ws.dto.locaux;

import ma.zyn.app.ws.dto.locataire.LocationDto;
import ma.zyn.app.ws.dto.locataire.StatutLocalDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocalDto  extends AuditBaseDto {

    private String code  ;
    private String adresse  ;
    private String dateCreation ;
    private String label  ;
    private Float superficie  ;
    private BigDecimal prix  ;
    private String description  ;
    private BigDecimal montantMensuel  ;

    private StatutLocalDto statutLocal ;
    private TypeLocalDto typeLocal ;

    private List<LocationDto> locations ;


    public LocalDto(){
        super();
    }




    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }


    public String getAdresse(){
        return this.adresse;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(String dateCreation){
        this.dateCreation = dateCreation;
    }


    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label = label;
    }


    public Float getSuperficie(){
        return this.superficie;
    }
    public void setSuperficie(Float superficie){
        this.superficie = superficie;
    }


    public BigDecimal getPrix(){
        return this.prix;
    }
    public void setPrix(BigDecimal prix){
        this.prix = prix;
    }


    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }


    public BigDecimal getMontantMensuel(){
        return this.montantMensuel;
    }
    public void setMontantMensuel(BigDecimal montantMensuel){
        this.montantMensuel = montantMensuel;
    }


    public StatutLocalDto getStatutLocal(){
        return this.statutLocal;
    }

    public void setStatutLocal(StatutLocalDto statutLocal){
        this.statutLocal = statutLocal;
    }
    public TypeLocalDto getTypeLocal(){
        return this.typeLocal;
    }

    public void setTypeLocal(TypeLocalDto typeLocal){
        this.typeLocal = typeLocal;
    }



    public List<LocationDto> getLocations(){
        return this.locations;
    }

    public void setLocations(List<LocationDto> locations){
        this.locations = locations;
    }



}
