package ma.zyn.app.bean.core.finance;








import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.math.BigDecimal;

@Entity
@Table(name = "caisse")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="caisse_seq",sequenceName="caisse_seq",allocationSize=1, initialValue = 1)
public class Caisse  extends BaseEntity     {




    @Column(length = 500)
    private String code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateCreation;

    @Column(length = 500)
    private String libelle;

    private BigDecimal solde = BigDecimal.ZERO;



    public Caisse(){
        super();
    }

    public Caisse(Long id){
        this.id = id;
    }

    public Caisse(Long id,String libelle){
        this.id = id;
        this.libelle = libelle ;
    }
    public Caisse(String libelle){
        this.libelle = libelle ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="caisse_seq")
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
    public String getLibelle(){
        return this.libelle;
    }
    public void setLibelle(String libelle){
        this.libelle = libelle;
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
        Caisse caisse = (Caisse) o;
        return id != null && id.equals(caisse.id);
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

