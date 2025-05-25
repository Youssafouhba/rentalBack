package  ma.zyn.app.ws.dto.locataire;

import ma.zyn.app.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;





@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeLocataireDto  extends AuditBaseDto {

    private Integer indexation  = 0 ;
    private String code  ;
    private String label  ;
    private String style  ;
    private String color  ;




    public TypeLocataireDto(){
        super();
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








}
