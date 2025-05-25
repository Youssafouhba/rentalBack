package ma.zyn.app.bean.core.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.bean.core.locataire.Locataire;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locataire.Transaction;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "compte_locataire")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="compte_locataire_seq", sequenceName="compte_locataire_seq", allocationSize=1, initialValue = 1)
public class CompteLocataire extends BaseEntity {


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

    private BigDecimal soldeInitial = BigDecimal.ZERO;

    private BigDecimal solde = BigDecimal.ZERO;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;

    private LocalDateTime dateCreation;
    private Locataire locataire;
    private List<Transaction> transactions;
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
    public CompteLocataire() {
        super();
    }

    public CompteLocataire(Long id) {
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


    private void recalcSolde() {
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

    @OneToMany(mappedBy = "compteLocataire",cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompteLocataire that = (CompteLocataire) o;
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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
