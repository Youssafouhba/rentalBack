package ma.zyn.app.bean.core.finance;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "banque")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="banque_seq",sequenceName="banque_seq",allocationSize=1, initialValue = 1)
public class Banque  extends BaseEntity     {




    @Column(length = 500)
    private String code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateCreation;

    @Column(length = 500)
    private String label;

    @Column(length = 500)
    private String nom;

    @Column(length = 500)
    private String numeroCompte;

    private BigDecimal solde = BigDecimal.ZERO;



    public Banque(){
        super();
    }

    public Banque(Long id){
        this.id = id;
    }

    public Banque(Long id,String label){
        this.id = id;
        this.label = label ;
    }
    public Banque(String label){
        this.label = label ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="banque_seq")
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
    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getNom(){
        return this.nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public String getNumeroCompte(){
        return this.numeroCompte;
    }
    public void setNumeroCompte(String numeroCompte){
        this.numeroCompte = numeroCompte;
    }
    public BigDecimal getSolde(){
        return this.solde;
    }
    public void setSolde(BigDecimal solde){
        this.solde = solde;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banque banque = (Banque) o;
        return id != null && id.equals(banque.id);
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
}

