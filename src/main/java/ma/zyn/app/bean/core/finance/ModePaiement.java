package ma.zyn.app.bean.core.finance;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.util.Objects;

@Entity
@Table(name = "mode_paiement")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="mode_paiement_seq",sequenceName="mode_paiement_seq",allocationSize=1, initialValue = 1)
public class ModePaiement  extends BaseEntity     {




    private Integer indexation = 0;

    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String label;

    @Column(length = 500)
    private String style;



    public ModePaiement(){
        super();
    }

    public ModePaiement(Long id){
        this.id = id;
    }

    public ModePaiement(Long id,String label){
        this.id = id;
        this.label = label ;
    }
    public ModePaiement(String label){
        this.label = label ;
    }
    public ModePaiement(String label,String code){
        this.label=label;
        this.code=code;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="mode_paiement_seq")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModePaiement modePaiement = (ModePaiement) o;
        return id != null && id.equals(modePaiement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

