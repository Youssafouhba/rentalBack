package ma.zyn.app.ws.dto.locataire;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.ws.dto.finance.CompteDto;
import ma.zyn.app.ws.dto.finance.CompteInstantaneeDto;
import ma.zyn.app.ws.dto.finance.CompteLocataireDto;
import ma.zyn.app.ws.dto.finance.TypePaiementDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto  extends AuditBaseDto {

    private String date ;
    private BigDecimal montant  ;
    private String description  ;
    private String code;



    private TypeTransactiontDto typeTransaction ;
    private ModePaiementDto modePaiement ;
    private TypePaiementDto typePaiement ;
    private CompteDto compteSource ;
    private CompteDto compteDestination ;
    private CompteLocataireDto compteLocataire;
    private CompteInstantaneeDto compteInstantanee;

    public TransactionDto(){
        super();
    }




    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
    }


    public BigDecimal getMontant(){
        return this.montant;
    }
    public void setMontant(BigDecimal montant){
        this.montant = montant;
    }


    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }


    public TypeTransactiontDto getTypeTransaction(){
        return this.typeTransaction;
    }

    public void setTypeTransaction(TypeTransactiontDto typeTransaction){
        this.typeTransaction = typeTransaction;
    }
    public ModePaiementDto getModePaiement(){
        return this.modePaiement;
    }

    public void setModePaiement(ModePaiementDto modePaiement){
        this.modePaiement = modePaiement;
    }
    public TypePaiementDto getTypePaiement(){
        return this.typePaiement;
    }

    public void setTypePaiement(TypePaiementDto typePaiement){
        this.typePaiement = typePaiement;
    }
    public CompteDto getCompteSource(){
        return this.compteSource;
    }

    public void setCompteSource(CompteDto compteSource){
        this.compteSource = compteSource;
    }
    public CompteDto getCompteDestination(){
        return this.compteDestination;
    }

    public void setCompteDestination(CompteDto compteDestination){
        this.compteDestination = compteDestination;
    }


    public CompteLocataireDto getCompteLocataire() {
        return compteLocataire;
    }

    public void setCompteLocataire(CompteLocataireDto compteLocataire) {
        this.compteLocataire = compteLocataire;
    }

    public CompteInstantaneeDto getCompteInstantanee() {
        return compteInstantanee;
    }

    public void setCompteInstantanee(CompteInstantaneeDto compteInstantanee) {
        this.compteInstantanee = compteInstantanee;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
