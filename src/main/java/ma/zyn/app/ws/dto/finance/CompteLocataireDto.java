package ma.zyn.app.ws.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.ws.dto.locataire.LocataireDto;
import ma.zyn.app.ws.dto.locataire.LocationDto;
import ma.zyn.app.ws.dto.locataire.TransactionDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.math.BigDecimal;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompteLocataireDto  extends AuditBaseDto {
    private BigDecimal soldeInitial = BigDecimal.ZERO;

    private BigDecimal solde  ;
    private BigDecimal debit  ;
    private BigDecimal credit  ;

    private LocataireDto locataire ;

    public LocataireDto getLocataire() {
        return locataire;
    }

    public LocationDto getLocation() {
        return location;
    }

    private LocationDto location;

    private List<TransactionDto> transactions ;


    public CompteLocataireDto() {

        super();
    }




    public BigDecimal getSolde(){
        return this.solde;
    }
    public void setSolde(BigDecimal solde){
        this.solde = solde;
    }


    public BigDecimal getDebit(){
        return this.debit;
    }
    public void setDebit(BigDecimal debit){
        this.debit = debit;
    }


    public BigDecimal getCredit(){
        return this.credit;
    }
    public void setCredit(BigDecimal credit){
        this.credit = credit;
    }



    public void setLocataire(LocataireDto locataire){
        this.locataire = locataire;
    }



    public List<TransactionDto> getTransactions(){
        return this.transactions;
    }

    public void setTransactions(List<TransactionDto> transactions){
        this.transactions = transactions;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }
}
