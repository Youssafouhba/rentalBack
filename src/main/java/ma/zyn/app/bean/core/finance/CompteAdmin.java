package ma.zyn.app.bean.core.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "compte_admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="compte_admin_seq",sequenceName="compte_admin_seq",allocationSize=1, initialValue = 1)
public class CompteAdmin extends BaseEntity {

    private String code;
    private BigDecimal solde = BigDecimal.ZERO;
    private LocalDateTime dateCreation;

    private List<Compte> comptes = new ArrayList<>();

    public CompteAdmin() {
        super();
    }

    public CompteAdmin(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compte_admin_seq")
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

    @OneToMany(mappedBy = "compteAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Compte> getComptes() {
        return this.comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
        updateSolde(); // Met à jour le solde lors de la définition des comptes
    }

    public void addCompte(Compte compte) {
        comptes.add(compte);
        compte.setCompteAdmin(this); // Associer le compte à ce CompteAdmin
        updateSolde(); // Met à jour le solde après ajout
    }

    public void removeCompte(Compte compte) {
        comptes.remove(compte);
        compte.setCompteAdmin(null); // Dissocier le compte
        updateSolde(); // Met à jour le solde après suppression
    }

    public void updateSolde() {
        if (comptes != null) {
            this.solde = comptes.stream()
                    .map(Compte::getSolde)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    public LocalDateTime getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompteAdmin compteAdmin = (CompteAdmin) o;
        return id != null && id.equals(compteAdmin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
