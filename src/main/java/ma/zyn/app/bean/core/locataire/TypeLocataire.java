package ma.zyn.app.bean.core.locataire;








import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "type_locataire")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="type_locataire_seq",sequenceName="type_locataire_seq",allocationSize=1, initialValue = 1)
public class TypeLocataire  extends BaseEntity     {




    private Integer indexation = 0;

    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String label;

    @Column(length = 500)
    private String style;

    @Column(length = 500)
    private String color;



    public TypeLocataire(){
        super();
    }

    public TypeLocataire(Long id){
        this.id = id;
    }

    public TypeLocataire(Long id,String label){
        this.id = id;
        this.label = label ;
    }
    public TypeLocataire(String label){
        this.label = label ;
    }
    public TypeLocataire(String label,String code){
        this.label=label;
        this.code=code;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="type_locataire_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    public Integer getIndexation(){
        return this.indexation;
    }
    public void setIndexation(Integer indexation){
        this.indexation = indexation;
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
    public String getStyle(){
        return this.style;
    }
    public void setStyle(String style){
        this.style = style;
    }
    public String getColor(){
        return this.color;
    }
    public void setColor(String color){
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeLocataire typeLocataire = (TypeLocataire) o;
        return id != null && id.equals(typeLocataire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

