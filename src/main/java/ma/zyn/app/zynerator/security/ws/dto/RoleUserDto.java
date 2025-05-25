package ma.zyn.app.zynerator.security.ws.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.dto.AuditBaseDto;





@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleUserDto  extends AuditBaseDto {


    private RoleDto role ;
    private UserDto user ;



    public RoleUserDto(){
        super();
    }




    public RoleDto getRole(){
        return this.role;
    }

    public void setRole(RoleDto role){
        this.role = role;
    }
    public UserDto getUser(){
        return this.user;
    }

    public void setUser(UserDto user){
        this.user = user;
    }






}
