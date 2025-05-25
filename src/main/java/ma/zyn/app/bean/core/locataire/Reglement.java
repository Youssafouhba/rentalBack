package ma.zyn.app.bean.core.locataire;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.bean.core.finance.Banque;
import ma.zyn.app.bean.core.finance.Caisse;
import ma.zyn.app.bean.core.finance.Compte;
import ma.zyn.app.bean.core.finance.ModePaiement;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reglement")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="reglement_seq",sequenceName="reglement_seq",allocationSize=1, initialValue = 1)
public class Reglement extends BaseEntity     {
    private String code;

    private BigDecimal montant = BigDecimal.ZERO;

    private LocalDateTime date ;

    private ModePaiement modePaiement ;

    @Column(length = 500)
    private String motif;

    private Compte compteSource;

    private Compte compteDestination;

    private Location location ;

    private Banque banque ;
    private Caisse caisse ;


    public Reglement(){
        super();
    }

    public Reglement(Long id){
        this.id = id;
    }





    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="reglement_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    public BigDecimal getMontant(){
        return this.montant;
    }
    public void setMontant(BigDecimal montant){
        this.montant = montant;
    }
    public LocalDateTime getDate(){
        return this.date;
    }
    public void setDate(LocalDateTime date){
        this.date = date;
    }
    public String getMotif(){
        return this.motif;
    }
    public void setMotif(String motif){
        this.motif = motif;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    public Location getLocation(){
        return this.location;
    }
    public void setLocation(Location location){
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reglement reglement = (Reglement) o;
        return id != null && id.equals(reglement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caisse")
    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banque")
    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_paiement")
    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compteDestination")
    public Compte getCompteDestination() {
        return compteDestination;
    }

    public void setCompteDestination(Compte compteDestination) {
        this.compteDestination = compteDestination;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compteSource")
    public Compte getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(Compte compteSource) {
        this.compteSource = compteSource;
    }
}

