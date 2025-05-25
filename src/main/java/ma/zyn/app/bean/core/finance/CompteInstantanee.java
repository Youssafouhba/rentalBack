package ma.zyn.app.bean.core.finance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "compte_instantanee")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="compte_instantanee_seq", sequenceName="compte_instantanee_seq", allocationSize=1, initialValue = 1)
public class CompteInstantanee extends BaseEntity {

    
    private BigDecimal solde = BigDecimal.ZERO;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;
    private BigDecimal soldeInitial = BigDecimal.ZERO;
    private List<Compte> comptes = new ArrayList<>();
    private Locataire locataire;
    private Location location;
    private Local locale;
    private String code;
    private String nom;
    private String description;

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateCreation;
    private List<Transaction> transactions;

    public CompteInstantanee() {
        super();
    }

    public CompteInstantanee(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compte_locataire_seq")
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSolde() {
        return this.solde;
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

    public void addCredit(BigDecimal montant) {
        credit = credit.add(montant);
        updateTotaux();
    }

    public void addDebit(BigDecimal montant) {
        debit = debit.add(montant);
        updateTotaux();
    }

    

    private void updateTotaux() {
        this.solde = credit.subtract(debit);
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locataire")
    public Locataire getLocataire() {
        return this.locataire;
    }

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
    }

    @OneToMany(mappedBy = "compteInstantanee")
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompteInstantanee that = (CompteInstantanee) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locaL")
    public Local getLocale() {
        return locale;
    }

    public void setLocale(Local locale) {
        this.locale = locale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @OneToMany(mappedBy = "compteInstantanee", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}
