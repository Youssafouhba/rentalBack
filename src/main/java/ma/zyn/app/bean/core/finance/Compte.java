package ma.zyn.app.bean.core.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.bean.core.locataire.Reglement;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "compte")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name = "compte_seq", sequenceName = "compte_seq", allocationSize = 1, initialValue = 1)
public class Compte extends BaseEntity {

    private BigDecimal solde = BigDecimal.ZERO;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    private BigDecimal soldeInitial = BigDecimal.ZERO;

    private Integer numeroCompte = 0;
    private LocalDateTime dateCreation;
    private String nom;
    private String code;
    private String description;

    // Relations
    private Banque banque;
    private Caisse caisse;
    private CompteAdmin compteAdmin;
    private CompteCharge compteCharge;
    private CompteInstantanee compteInstantanee;
    private List<Transaction> transactions = new ArrayList<>();
    private List<Transaction> transactionsSource = new ArrayList<>();
    private List<Transaction> transactionsDestination = new ArrayList<>();
    private List<Reglement> reglements = new ArrayList<>();
    private List<Reglement> reglementsSource = new ArrayList<>();

    public Compte() {
        super();
    }

    public Compte(Long id) {
        this.id = id;
    }

    /**
     * Initialise le compte avec un solde initial (positif ou négatif).
     */
    public void initialize(BigDecimal initial) {
        this.soldeInitial = initial;
        if (initial.compareTo(BigDecimal.ZERO) >= 0) {
            this.credit = initial;
            this.debit = BigDecimal.ZERO;
            recalcSolde();
        } else {
            this.credit = BigDecimal.ZERO;
            this.debit = initial.abs();
            recalcSolde();
        }
    }

    public void addCredit(BigDecimal montant) {
        this.credit = this.credit.add(montant);
        recalcSolde();
    }

    public void subtractCredit(BigDecimal montant) {
        this.credit = this.credit.subtract(montant);
        recalcSolde();
    }

    public void addDebit(BigDecimal montant) {
        this.debit = this.debit.add(montant);
        recalcSolde();
    }
    public void subtractDebit(BigDecimal montant) {
        this.debit = this.debit.subtract(montant);
        recalcSolde();
    }

    public void recalcSolde() {
        this.solde = credit.subtract(debit);
    }

    /* Getters & Setters */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compte_seq")
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public Integer getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(Integer numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banque")
    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
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
    @JoinColumn(name = "compteAdmin")
    public CompteAdmin getCompteAdmin() {
        return compteAdmin;
    }

    public void setCompteAdmin(CompteAdmin compteAdmin) {
        this.compteAdmin = compteAdmin;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compteCharge")
    public CompteCharge getCompteCharge() {
        return compteCharge;
    }

    public void setCompteCharge(CompteCharge compteCharge) {
        this.compteCharge = compteCharge;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compteInstantanee")
    public CompteInstantanee getCompteInstantanee() {
        return compteInstantanee;
    }

    public void setCompteInstantanee(CompteInstantanee ci) {
        this.compteInstantanee = ci;
    }

    @OneToMany(mappedBy = "compteSource", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<Transaction> getTransactionsSource() {
        return transactionsSource;
    }

    public void setTransactionsSource(List<Transaction> transactionsSource) {
        this.transactionsSource = transactionsSource;
    }

    @OneToMany(mappedBy = "compteDestination",  cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE})
    public List<Transaction> getTransactionsDestination() {
        return transactionsDestination;
    }

    public void setTransactionsDestination(List<Transaction> transactionsDestination) {
        this.transactionsDestination = transactionsDestination;
    }

    @OneToMany(mappedBy = "compteDestination", cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE})
    public List<Reglement> getReglements() {
        return reglements;
    }

    public void setReglements(List<Reglement> reglements) {
        this.reglements = reglements;
    }

    @OneToMany(mappedBy = "compteSource", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public List<Reglement> getReglementsSource() {
        return reglementsSource;
    }

    public void setReglementsSource(List<Reglement> reglementsSource) {
        this.reglementsSource = reglementsSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compte)) return false;
        Compte compte = (Compte) o;
        return id != null && id.equals(compte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @OneToMany(mappedBy = "compteSource", cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE})
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
