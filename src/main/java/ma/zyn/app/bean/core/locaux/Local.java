package ma.zyn.app.bean.core.locaux;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;




import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.bean.core.locataire.Location;
import ma.zyn.app.bean.core.locataire.StatutLocal;
import ma.zyn.app.bean.core.locaux.TypeLocal;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;
import java.math.BigDecimal;

@Entity
@Table(name = "local")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="local_seq",sequenceName="local_seq",allocationSize=1, initialValue = 1)
public class Local  extends BaseEntity     {




    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String adresse;

    private LocalDateTime dateCreation ;

    @Column(length = 500)
    private String label;

    private Float superficie ;

    private BigDecimal prix = BigDecimal.ZERO;

    private String description;

    private BigDecimal montantMensuel = BigDecimal.ZERO;

    private StatutLocal statutLocal ;
    private TypeLocal typeLocal ;

    private List<Location> locations ;

    private List<CompteCharge> compteCharges = new ArrayList<>();

    public Local(){
        super();
    }

    public Local(Long id){
        this.id = id;
    }

    public Local(Long id,String code){
        this.id = id;
        this.code = code ;
    }
    public Local(String code){
        this.code = code ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="local_seq")
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
    public String getAdresse(){
        return this.adresse;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }
    public LocalDateTime getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(LocalDateTime dateCreation){
        this.dateCreation = dateCreation;
    }
    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public Float getSuperficie(){
        return this.superficie;
    }
    public void setSuperficie(Float superficie){
        this.superficie = superficie;
    }
    public BigDecimal getPrix(){
        return this.prix;
    }
    public void setPrix(BigDecimal prix){
        this.prix = prix;
    }
    @Column(columnDefinition="TEXT")
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public BigDecimal getMontantMensuel(){
        return this.montantMensuel;
    }
    public void setMontantMensuel(BigDecimal montantMensuel){
        this.montantMensuel = montantMensuel;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statut_local")
    public StatutLocal getStatutLocal(){
        return this.statutLocal;
    }
    public void setStatutLocal(StatutLocal statutLocal){
        this.statutLocal = statutLocal;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_local")
    public TypeLocal getTypeLocal(){
        return this.typeLocal;
    }
    public void setTypeLocal(TypeLocal typeLocal){
        this.typeLocal = typeLocal;
    }
    @OneToMany(mappedBy = "local")
    public List<Location> getLocations(){
        return this.locations;
    }

    public void setLocations(List<Location> locations){
        this.locations = locations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local local = (Local) o;
        return id != null && id.equals(local.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CompteCharge> getCompteCharges() {
        return compteCharges;
    }

    public void setCompteCharges(List<CompteCharge> compteCharges) {
        this.compteCharges = compteCharges;
    }
}

