package ma.zyn.app.bean.core.locataire;


import java.time.LocalDateTime;


import java.util.ArrayList;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;




import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.bean.core.finance.CompteInstantanee;
import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.math.BigDecimal;

@Entity
@Table(name = "location")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="location_seq",sequenceName="location_seq",allocationSize=1, initialValue = 1)
public class Location  extends BaseEntity     {



    private List<Reglement> reglements = new ArrayList<>();
    private List<Avoir> avoirs = new ArrayList<>();
    @Column(length = 500)
    private String code;

    private BigDecimal solde = BigDecimal.ZERO;

    @Column(length = 500)
    private String reference;

    private LocalDateTime dateCreation ;

    private LocalDateTime dateDebut ;

    private LocalDateTime dateFin ;

    private BigDecimal loyer = BigDecimal.ZERO;
    private List<CompteInstantanee> compteInstantanees = new ArrayList<>();
    private BigDecimal caution = BigDecimal.ZERO;
    private boolean isActif ;
    private Locataire locataire ;
    private Local local ;


    public Location(){
        super();
    }

    public Location(Long id){
        this.id = id;
    }

    public Location(Long id,String code){
        this.id = id;
        this.code = code ;
    }
    public Location(String code){
        this.code = code ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="location_seq")
    @Override
    public Long getId(){
        return this.id;
    }
    @Override
    public void setId(Long id){
        this.id = id;
    }
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public LocalDateTime getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(LocalDateTime dateCreation){
        this.dateCreation = dateCreation;
    }
    public LocalDateTime getDateDebut(){
        return this.dateDebut;
    }
    public void setDateDebut(LocalDateTime dateDebut){
        this.dateDebut = dateDebut;
    }
    public LocalDateTime getDateFin(){
        return this.dateFin;
    }
    public void setDateFin(LocalDateTime dateFin){
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locataire")
    public Locataire getLocataire(){
        return this.locataire;
    }
    public void setLocataire(Locataire locataire){
        this.locataire = locataire;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local")
    public Local getLocal(){
        return this.local;
    }
    public void setLocal(Local local){
        this.local = local;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id != null && id.equals(location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Boolean getActif() {
        return isActif;
    }

    public void setActif(Boolean actif) {
        isActif = actif;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CompteInstantanee> getCompteInstantanees() {
        return compteInstantanees;
    }

    public void setCompteInstantanees(List<CompteInstantanee> compteInstantanees) {
        this.compteInstantanees = compteInstantanees;
    }


    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Reglement> getReglements() {
        return reglements;
    }

    public void setReglements(List<Reglement> reglements) {
        this.reglements = reglements;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    @OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE})
    public List<Avoir> getAvoirs() {
        return avoirs;
    }

    public void setAvoirs(List<Avoir> avoirs) {
        this.avoirs = avoirs;
    }
}

