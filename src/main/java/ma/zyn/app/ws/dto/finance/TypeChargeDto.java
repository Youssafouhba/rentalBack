package ma.zyn.app.ws.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.bean.core.finance.CompteCharge;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.util.HashSet;
import java.util.Set;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeChargeDto  extends AuditBaseDto {

    private String code  ;
    private String label  ;
    private String style  ;
    private String description  ;
    private Set<CompteCharge> comptesCharges = new HashSet<>();




    public TypeChargeDto(){
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


    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public Set<CompteCharge> getComptesCharges() {
        return comptesCharges;
    }

    public void setComptesCharges(Set<CompteCharge> comptesCharges) {
        this.comptesCharges = comptesCharges;
    }
}
