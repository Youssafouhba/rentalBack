package ma.zyn.app.zynerator.security.ws.converter;

import ma.zyn.app.zynerator.converter.AbstractConverter;
import ma.zyn.app.zynerator.security.bean.RoleUser;
import ma.zyn.app.zynerator.security.bean.User;
import ma.zyn.app.zynerator.security.ws.dto.RoleUserDto;
import ma.zyn.app.zynerator.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleUserConverter extends AbstractConverter<RoleUser, RoleUserDto> {

    @Autowired
    private RoleConverter roleConverter ;
    @Autowired
    private UserConverter utilisateurConverter ;
    private boolean role;
    private boolean user;

    public  RoleUserConverter(){//Role roleUser user,){
        super(RoleUser.class, RoleUserDto.class);
        //this.role =  role ;
        //this.user =  user ;
    }

    @Override
    public RoleUser toItem(RoleUserDto dto) {
        if (dto == null) {
            return null;
        } else {
        RoleUser item = new RoleUser();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(this.role && dto.getRole()!=null &&  dto.getRole().getId() != null)
                item.setRole(roleConverter.toItem(dto.getRole())) ;

            if(dto.getUser() != null && dto.getUser().getId() != null){
                item.setUserApp(new User());
                item.getUserApp().setId(dto.getUser().getId());
                item.getUserApp().setEmail(dto.getUser().getEmail());
            }




        return item;
        }
    }

    @Override
    public RoleUserDto toDto(RoleUser item) {
        if (item == null) {
            return null;
        } else {
            RoleUserDto dto = new RoleUserDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
        if(this.role && item.getRole()!=null) {
            dto.setRole(roleConverter.toDto(item.getRole())) ;
        }
        if(this.user && item.getUserApp()!=null) {
            dto.setUser(utilisateurConverter.toDto(item.getUserApp())) ;
        }


        return dto;
        }
    }


    public void initObject(boolean value) {
        this.role = value;
        this.user = value;
    }


    public RoleConverter getRoleConverter(){
        return this.roleConverter;
    }
    public void setRoleConverter(RoleConverter roleConverter ){
        this.roleConverter = roleConverter;
    }
    public UserConverter getUserConverter(){
        return this.utilisateurConverter;
    }
    public void setUserConverter(UserConverter utilisateurConverter ){
        this.utilisateurConverter = utilisateurConverter;
    }
    public boolean  isRole(){
        return this.role;
    }
    public void  setRole(boolean role){
        this.role = role;
    }
    public boolean  isUser(){
        return this.user;
    }
    public void  setUser(boolean user){
        this.user = user;
    }
}
