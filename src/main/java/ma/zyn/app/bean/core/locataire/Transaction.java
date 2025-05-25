package ma.zyn.app.bean.core.locataire;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.bean.core.finance.*;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="transaction_seq",sequenceName="transaction_seq",allocationSize=1, initialValue = 1)
public class Transaction  extends BaseEntity     {




    private LocalDateTime date ;

    private String code;

    private BigDecimal montant = BigDecimal.ZERO;

    @Column(length = 500)
    private String description;

    private TypeTransactiont typeTransaction ;
    private ModePaiement modePaiement ;
    private TypePaiement typePaiement ;
    private Compte compteSource ;
    private Compte compteDestination ;
    private CompteLocataire compteLocataire;
    private CompteInstantanee compteInstantanee;



    public Transaction(){
        super();
    }

    public Transaction(Long id){
        this.id = id;
    }





    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="transaction_seq")
    @Override
    public Long getId(){
        return this.id;
    }
    @Override
    public void setId(Long id){
        this.id = id;
    }
    public LocalDateTime getDate(){
        return this.date;
    }
    public void setDate(LocalDateTime date){
        this.date = date;
    }
    public BigDecimal getMontant(){
        return this.montant;
    }
    public void setMontant(BigDecimal montant){
        this.montant = montant;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_transaction")
    public TypeTransactiont getTypeTransaction(){
        return this.typeTransaction;
    }
    public void setTypeTransaction(TypeTransactiont typeTransaction){
        this.typeTransaction = typeTransaction;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_paiement")
    public ModePaiement getModePaiement(){
        return this.modePaiement;
    }
    public void setModePaiement(ModePaiement modePaiement){
        this.modePaiement = modePaiement;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_paiement")
    public TypePaiement getTypePaiement(){
        return this.typePaiement;
    }
    public void setTypePaiement(TypePaiement typePaiement){
        this.typePaiement = typePaiement;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_source")
    public Compte getCompteSource(){
        return this.compteSource;
    }
    public void setCompteSource(Compte compteSource){
        this.compteSource = compteSource;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_destination")
    public Compte getCompteDestination(){
        return this.compteDestination;
    }
    public void setCompteDestination(Compte compteDestination){
        this.compteDestination = compteDestination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return id != null && id.equals(transaction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_locataire")
    public CompteLocataire getCompteLocataire() {
        return compteLocataire;
    }

    public void setCompteLocataire(CompteLocataire compteLocataire) {
        this.compteLocataire = compteLocataire;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_instantanee")
    public CompteInstantanee getCompteInstantanee() {
        return compteInstantanee;
    }

    public void setCompteInstantanee(CompteInstantanee compteInstantanee) {
        this.compteInstantanee = compteInstantanee;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

