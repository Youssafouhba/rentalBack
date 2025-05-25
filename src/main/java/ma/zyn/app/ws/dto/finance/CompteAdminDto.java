package ma.zyn.app.ws.dto.finance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompteAdminDto  extends AuditBaseDto {
    private String code;

    private BigDecimal solde  ;
    private String dateCreation ;


    private List<CompteDto> comptes = new ArrayList<>();;


    public CompteAdminDto(){
        super();
    }




    public BigDecimal getSolde() {
        return this.solde;
    }

    public void setSolde(BigDecimal solde){
        this.solde = solde;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(String dateCreation){
        this.dateCreation = dateCreation;
    }





    public List<CompteDto> getComptes(){
        return this.comptes;
    }

    public void setComptes(List<CompteDto> comptes){
        this.comptes = comptes;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
