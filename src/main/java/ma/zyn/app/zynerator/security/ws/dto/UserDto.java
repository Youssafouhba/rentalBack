package ma.zyn.app.zynerator.security.ws.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.dto.AuditBaseDto;

import java.util.List;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto  extends AuditBaseDto {

    protected Boolean credentialsNonExpired  ;
    protected Boolean enabled  ;
    protected String email  ;
    protected Boolean accountNonExpired  ;
    protected Boolean accountNonLocked  ;
    protected String username  ;
    protected String password  ;
    protected Boolean passwordChanged  ;

    protected String firstName;
    protected String lastName;
    protected String phone;

    protected String fullName;
    protected String avatar;
    protected String about;

    protected List<ModelPermissionUserDto> modelPermissionUsers ;
    protected List<RoleUserDto> roleUsers ;

    protected String linkValidationCode;
    protected LocalDateTime expirationLinkDate;


    public String getLinkValidationCode() {
        return linkValidationCode;
    }

    public void setLinkValidationCode(String linkValidationCode) {
        this.linkValidationCode = linkValidationCode;
    }

    public LocalDateTime getExpirationLinkDate() {
        return expirationLinkDate;
    }

    public void setExpirationLinkDate(LocalDateTime expirationLinkDate) {
        this.expirationLinkDate = expirationLinkDate;
    }

    public UserDto(){
        super();
    }

    public Boolean getCredentialsNonExpired(){
        return this.credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired){
        this.credentialsNonExpired = credentialsNonExpired;
    }


    public Boolean getEnabled(){
        return this.enabled;
    }
    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }


    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Boolean getAccountNonExpired(){
        return this.accountNonExpired;
    }
    public void setAccountNonExpired(Boolean accountNonExpired){
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked(){
        return this.accountNonLocked;
    }
    public void setAccountNonLocked(Boolean accountNonLocked){
        this.accountNonLocked = accountNonLocked;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }


    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public Boolean getPasswordChanged(){
        return this.passwordChanged;
    }
    public void setPasswordChanged(Boolean passwordChanged){
        this.passwordChanged = passwordChanged;
    }

    public List<ModelPermissionUserDto> getModelPermissionUsers(){
        return this.modelPermissionUsers;
    }

    public void setModelPermissionUsers(List<ModelPermissionUserDto> modelPermissionUsers){
        this.modelPermissionUsers = modelPermissionUsers;
    }
    public List<RoleUserDto> getRoleUsers(){
        return this.roleUsers;
    }

    public void setRoleUsers(List<RoleUserDto> roleUsers){
        this.roleUsers = roleUsers;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
                        
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }  
                        
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }   
                        
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
