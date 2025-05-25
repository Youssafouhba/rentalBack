package ma.zyn.app.bean.core.finance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
        import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "compte_charge")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="compte_charge_seq", sequenceName="compte_charge_seq", allocationSize=1, initialValue = 1)
public class CompteCharge extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compte_charge_seq")
    private Long id;

    @Column(length = 500)
    private String code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateCreation;


    @Column(length = 500)
    private String label;

    @Column(length = 500)
    private String nom;

    private BigDecimal solde = BigDecimal.ZERO;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local")
    private Local local;

    @OneToMany(mappedBy = "compteCharge",  cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE})
    private List<Compte> comptes = new ArrayList<>();

    // Relations avec TypeCharge - utiliser mappedBy pour le côté non propriétaire de la relation
    @ManyToMany(mappedBy = "comptesCharges", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<TypeCharge> typeCharges = new HashSet<>();

    // Relations avec Charge
    @OneToMany(mappedBy = "compteCharge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Charge> charges = new ArrayList<>();

    // Constructeurs
    public CompteCharge() {
        super();
    }

    public CompteCharge(Long id) {
        this.id = id;
    }

    public CompteCharge(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public CompteCharge(String label) {
        this.label = label;
    }

    // Accesseurs et mutateurs
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Charge> getCharges() {
        return this.charges;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    public BigDecimal getSolde() {
        return this.solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Set<TypeCharge> getTypeCharges() {
        return typeCharges;
    }

    public void setTypeCharges(Set<TypeCharge> typeCharges) {
        this.typeCharges = typeCharges;
    }

    // Méthodes utilitaires
    public void addCharge(Charge charge) {
        charges.add(charge);
        charge.setCompteCharge(this);
    }
    @PreRemove
    private void preRemove() {
        // Iterate through all associated TypeCharges and remove this CompteCharge
        for (TypeCharge typeCharge : typeCharges) {
            typeCharge.getComptesCharges().remove(this);
        }
        typeCharges.clear();
    }


    public void removeCharge(Charge charge) {
        charges.remove(charge);
        charge.setCompteCharge(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompteCharge compteCharge = (CompteCharge) o;
        return id != null && id.equals(compteCharge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}