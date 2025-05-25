package ma.zyn.app.zynerator.security.bean;

import ma.zyn.app.zynerator.bean.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_app")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "user_app_seq", sequenceName = "user_app_seq", allocationSize = 1, initialValue = 1)
public class User  extends BaseEntity  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_app_seq")
    protected Long id;
    protected boolean credentialsNonExpired = true;
    protected boolean enabled = true;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected String email;
    protected boolean accountNonExpired = true;
    protected boolean accountNonLocked = true;
    protected String username;
    protected String password;
    protected boolean passwordChanged = false;


    protected String fullName;
    protected String avatar;
    protected String about;

    protected String firstName;
    protected String lastName;
    protected String phone;
    protected String linkValidationCode;
    protected LocalDateTime expirationLinkDate;


    protected int loginAttempts;
    protected LocalDateTime lockoutTime;


    @Transient
    protected Collection<GrantedAuthority> authorities;
    @OneToMany(mappedBy = "userApp")
    protected List<ModelPermissionUser> modelPermissionUsers;
    @OneToMany(mappedBy = "userApp")
    protected List<RoleUser> roleUsers;


    public User() {
         super();
    }

    public User(Long id,String email) {
        this.id = id;
        this.email = email;
    }

    public User(String username) {
        this.username = username;
        this.password = username;
        this.email = username;
    }


    public boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean getPasswordChanged() {
        return passwordChanged;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }




    public List<ModelPermissionUser> getModelPermissionUsers() {
        return this.modelPermissionUsers;
    }

    public void setModelPermissionUsers(List<ModelPermissionUser> modelPermissionUsers) {
        this.modelPermissionUsers = modelPermissionUsers;
    }



    public List<RoleUser> getRoleUsers() {
        return this.roleUsers;
    }

    public void setRoleUsers(List<RoleUser> roleUsers) {
        this.roleUsers = roleUsers;
    }

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

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public LocalDateTime getLockoutTime() {
        return lockoutTime;
    }

    public void setLockoutTime(LocalDateTime lockoutTime) {
        this.lockoutTime = lockoutTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
