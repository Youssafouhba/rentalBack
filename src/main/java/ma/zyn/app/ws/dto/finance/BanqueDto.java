package  ma.zyn.app.ws.dto.finance;

import ma.zyn.app.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BanqueDto  extends AuditBaseDto {

    private String code  ;
    private String label  ;
    private String nom  ;
    private String numeroCompte  ;
    private BigDecimal solde  ;

    private String dateCreation;



    public BanqueDto(){
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


    public String getNumeroCompte(){
        return this.numeroCompte;
    }
    public void setNumeroCompte(String numeroCompte){
        this.numeroCompte = numeroCompte;
    }


    public BigDecimal getSolde(){
        return this.solde;
    }
    public void setSolde(BigDecimal solde){
        this.solde = solde;
    }


    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
}
