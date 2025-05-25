package ma.zyn.app.ws.dto.locataire;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.ws.dto.finance.CompteLocataireDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocataireDto  extends AuditBaseDto {

    private String code  ;
    private String nom  ;
    private String prenom  ;
    private String telephone  ;
    private String dateCreation ;
    private String fullName;

    private TypeLocataireDto typeLocataire ;
    private CompteLocataireDto compteLocataire ;

    private List<LocationDto> locations ;
    private List<AvoirDto> avoirs ;


    public LocataireDto(){
        super();
    }




    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }


    public String getNom(){
        return this.nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }


    public String getPrenom(){
        return this.prenom;
    }
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }


    public String getTelephone(){
        return this.telephone;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(String dateCreation){
        this.dateCreation = dateCreation;
    }


    public TypeLocataireDto getTypeLocataire(){
        return this.typeLocataire;
    }

    public void setTypeLocataire(TypeLocataireDto typeLocataire){
        this.typeLocataire = typeLocataire;
    }
    public CompteLocataireDto getCompteLocataire(){
        return this.compteLocataire;
    }

    public void setCompteLocataire(CompteLocataireDto compteLocataire){
        this.compteLocataire = compteLocataire;
    }



    public List<LocationDto> getLocations(){
        return this.locations;
    }

    public void setLocations(List<LocationDto> locations){
        this.locations = locations;
    }
    public List<AvoirDto> getAvoirs(){
        return this.avoirs;
    }

    public void setAvoirs(List<AvoirDto> avoirs){
        this.avoirs = avoirs;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
