package ma.zyn.app.zynerator.security.payload.request;

import jakarta.validation.constraints.NotBlank;

public class ActivationRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
    @NotBlank
    private String activationCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
