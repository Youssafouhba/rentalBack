package ma.zyn.app.ws.dto.locataire;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.dto.AuditBaseDto;





@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeTransactiontDto  extends AuditBaseDto {

    private String code  ;
    private String label  ;
    private String style  ;




    public TypeTransactiontDto(){
        super();
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
