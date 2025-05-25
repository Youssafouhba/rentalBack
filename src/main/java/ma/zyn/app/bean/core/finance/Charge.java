package ma.zyn.app.bean.core.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.bean.core.locaux.Local;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "charge")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="charge_seq", sequenceName="charge_seq", allocationSize=1, initialValue = 1)
public class Charge extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "charge_seq")
    private Long id;

    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String label;

    private BigDecimal montant = BigDecimal.ZERO;

    private LocalDateTime date;

    @Column(columnDefinition = "boolean default false")
    private Boolean isPaid = false;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_paiement")
    private ModePaiement modePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_charge")
    private TypeCharge typeCharge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local")
    private Local local;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_source", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Compte compteSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_charge")
    private CompteCharge compteCharge;

    // Constructeurs
    public Charge() {
        super();
    }

    public Charge(Long id) {
        this.id = id;
    }

    public Charge(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Charge(String label) {
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

    public BigDecimal getMontant() {
        return this.montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getIsPaid() {
        return this.isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeCharge getTypeCharge() {
        return this.typeCharge;
    }

    public void setTypeCharge(TypeCharge typeCharge) {
        this.typeCharge = typeCharge;
    }

    public Local getLocal() {
        return this.local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public CompteCharge getCompteCharge() {
        return compteCharge;
    }

    public void setCompteCharge(CompteCharge compteCharge) {
        this.compteCharge = compteCharge;
    }

    public Compte getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(Compte compteSource) {
        this.compteSource = compteSource;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return id != null && id.equals(charge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}