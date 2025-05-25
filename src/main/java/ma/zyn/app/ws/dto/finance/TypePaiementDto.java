package ma.zyn.app.ws.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.dto.AuditBaseDto;





@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypePaiementDto  extends AuditBaseDto {

    private Integer indexation  = 0 ;
    private String code  ;
    private String label  ;
    private String style  ;




    public TypePaiementDto(){
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








}
