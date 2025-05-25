package ma.zyn.app.bean.core.locataire;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.bean.core.finance.CompteLocataire;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "locataire")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="locataire_seq",sequenceName="locataire_seq",allocationSize=1, initialValue = 1)
public class Locataire extends BaseEntity     {




    @Column(length = 500)
    private String code;


    @Column(length = 500)
    private String nom;

    @Column(length = 500)
    private String prenom;

    @Column(length = 500)
    private String telephone;

    private String fullName;
    private LocalDateTime dateCreation ;

    private TypeLocataire typeLocataire ;
    private CompteLocataire compteLocataire ;

    private List<Location> locations ;
    private List<Avoir> avoirs ;

    public Locataire(){
        super();
    }

    public Locataire(Long id){
        this.id = id;
    }

    public Locataire(Long id, String code){
        this.id = id;
        this.code = code ;
    }
    public Locataire(String code){
        this.code = code ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="locataire_seq")
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
    public String getNom(){
        return this.nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public String getPrenom(){
        return this.prenom;
    }
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    public String getTelephone(){
        return this.telephone;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public LocalDateTime getDateCreation(){
        return this.dateCreation;
    }
    public void setDateCreation(LocalDateTime dateCreation){
        this.dateCreation = dateCreation;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_locataire")
    public TypeLocataire getTypeLocataire(){
        return this.typeLocataire;
    }
    public void setTypeLocataire(TypeLocataire typeLocataire){
        this.typeLocataire = typeLocataire;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_locataire")
    public CompteLocataire getCompteLocataire(){
        return this.compteLocataire;
    }
    public void setCompteLocataire(CompteLocataire compteLocataire){
        this.compteLocataire = compteLocataire;
    }
    @OneToMany(mappedBy = "locataire")
    public List<Location> getLocations(){
        return this.locations;
    }

    public void setLocations(List<Location> locations){
        this.locations = locations;
    }
    @OneToMany(mappedBy = "locataire")
    public List<Avoir> getAvoirs(){
        return this.avoirs;
    }

    public void setAvoirs(List<Avoir> avoirs){
        this.avoirs = avoirs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locataire locataire = (Locataire) o;
        return id != null && id.equals(locataire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

