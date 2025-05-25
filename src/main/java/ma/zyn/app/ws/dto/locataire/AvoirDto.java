package ma.zyn.app.ws.dto.locataire;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.ws.dto.finance.BanqueDto;
import ma.zyn.app.ws.dto.finance.CaisseDto;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.math.BigDecimal;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvoirDto  extends AuditBaseDto {
    private String code;
    private String compteSource;

    private ModePaiementDto modePaiement ;
    private BigDecimal montant  ;
    private String date ;
    private String motif  ;

    private LocationDto location ;
    private BanqueDto banque ;
    private CaisseDto caisse ;
    private LocataireDto locataire ;

    public AvoirDto(){
        super();
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


    public String getMotif(){
        return this.motif;
    }
    public void setMotif(String motif){
        this.motif = motif;
    }


    public LocationDto getLocation(){
        return this.location;
    }

    public void setLocation(LocationDto location){
        this.location = location;
    }


    public LocataireDto getLocataire() {
        return locataire;
    }

    public void setLocataire(LocataireDto locataire) {
        this.locataire = locataire;
    }

    public CaisseDto getCaisse() {
        return caisse;
    }

    public void setCaisse(CaisseDto caisse) {
        this.caisse = caisse;
    }

    public BanqueDto getBanque() {
        return banque;
    }

    public void setBanque(BanqueDto banque) {
        this.banque = banque;
    }

    public ModePaiementDto getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiementDto modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(String compteSource) {
        this.compteSource = compteSource;
    }
}
