package ma.zyn.app.bean.core.locataire;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import ma.zyn.app.zynerator.bean.BaseEntity;

import java.util.Objects;

@Entity
@Table(name = "type_transactiont")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="type_transactiont_seq",sequenceName="type_transactiont_seq",allocationSize=1, initialValue = 1)
public class TypeTransactiont  extends BaseEntity     {




    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String label;

    @Column(length = 500)
    private String style;



    public TypeTransactiont(){
        super();
    }

    public TypeTransactiont(Long id){
        this.id = id;
    }

    public TypeTransactiont(Long id,String label){
        this.id = id;
        this.label = label ;
    }
    public TypeTransactiont(String label){
        this.label = label ;
    }
    public TypeTransactiont(String label,String code){
        this.label=label;
        this.code=code;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="type_transactiont_seq")
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
        TypeTransactiont typeTransactiont = (TypeTransactiont) o;
        return id != null && id.equals(typeTransactiont.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

