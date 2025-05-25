package ma.zyn.app.ws.dto.finance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.ws.dto.locataire.LocataireDto;
import ma.zyn.app.ws.dto.locataire.LocationDto;
import ma.zyn.app.ws.dto.locataire.TransactionDto;
import ma.zyn.app.ws.dto.locaux.LocalDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;
import org.apache.poi.util.LocaleID;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompteInstantaneeDto extends AuditBaseDto {

    private BigDecimal solde = BigDecimal.ZERO;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;
    private BigDecimal soldeInitial = BigDecimal.ZERO;

    private String description;

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private String dateCreation;


    public LocataireDto getLocataire() {
        return locataire;
    }

    public void setLocataire(LocataireDto locataire) {
        this.locataire = locataire;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }

    private LocataireDto locataire;
    private LocationDto location;
    private LocalDto locale;
    private String code;
    private String nom;
    private List<TransactionDto> transactions;

    public LocalDto getLocale() {
        return locale;
    }

    public void setLocale(LocalDto locale) {
        this.locale = locale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
