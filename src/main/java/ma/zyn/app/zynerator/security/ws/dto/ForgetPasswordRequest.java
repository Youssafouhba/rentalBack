package ma.zyn.app.zynerator.security.ws.dto;

public class ForgetPasswordRequest {
	private String email;
    private String newPassword;
    private String linkValidationCode;

    public String getLinkValidationCode() {
        return linkValidationCode;
    }

    public void setLinkValidationCode(String linkValidationCode) {
        this.linkValidationCode = linkValidationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
