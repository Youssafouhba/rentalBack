package ma.zyn.app.bean.core.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "type_charge")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="type_charge_seq", sequenceName="type_charge_seq", allocationSize=1, initialValue = 1)
public class TypeCharge extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_charge_seq")
    private Long id;

    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String label;

    @Column(length = 500)
    private String style;

    @Column(length = 500)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "type_charge_compte_charge",
            joinColumns = @JoinColumn(name = "type_charge"),
            inverseJoinColumns = @JoinColumn(name = "compte_charge")
    )
    private Set<CompteCharge> comptesCharges = new HashSet<>();

    // Relations One-to-Many avec Charge
    @OneToMany(mappedBy = "typeCharge")
    private Set<Charge> charges = new HashSet<>();

    // Constructeurs
    public TypeCharge() {
        super();
    }

    public TypeCharge(Long id) {
        this.id = id;
    }

    public TypeCharge(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public TypeCharge(String label) {
        this.label = label;
    }

    public TypeCharge(String label, String code) {
        this.label = label;
        this.code = code;
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

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CompteCharge> getComptesCharges() {
        return comptesCharges;
    }

    public void setComptesCharges(Set<CompteCharge> comptesCharges) {
        this.comptesCharges = comptesCharges;
    }

    public Set<Charge> getCharges() {
        return charges;
    }

    public void setCharges(Set<Charge> charges) {
        this.charges = charges;
    }

    // Méthodes utilitaires
    public void addCompteCharge(CompteCharge compteCharge) {
        this.comptesCharges.add(compteCharge);
        compteCharge.getTypeCharges().add(this);
    }

    public void removeCompteCharge(CompteCharge compteCharge) {
        this.comptesCharges.remove(compteCharge);
        compteCharge.getTypeCharges().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeCharge typeCharge = (TypeCharge) o;
        return id != null && id.equals(typeCharge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
