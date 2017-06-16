package coffee.synyx.auth;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Configuration properties of the {@code AuthenticationServer}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Yannic Klem - klem@synyx.de
 */
@ConfigurationProperties(prefix = "auth")
public class AuthConfigurationProperties {

    private boolean development = true;

    @URL
    @NotBlank
    private String defaultRedirectUrl = "http://localhost:8080";

    @NotBlank(message = "Please provide the service for the password recovery")
    private String passwordRecoveryServiceName = "profile";

    @NotBlank(message = "Please provide the path to the password recovery without starting /")
    private String passwordRecoveryPath = "/password-recovery";

    public boolean isDevelopment() {

        return development;
    }


    public void setDevelopment(boolean development) {

        this.development = development;
    }


    public String getDefaultRedirectUrl() {

        return defaultRedirectUrl;
    }


    public void setDefaultRedirectUrl(String defaultRedirectUrl) {

        this.defaultRedirectUrl = defaultRedirectUrl;
    }


    public String getPasswordRecoveryServiceName() {

        return passwordRecoveryServiceName;
    }


    public void setPasswordRecoveryServiceName(String passwordRecoveryServiceName) {

        this.passwordRecoveryServiceName = passwordRecoveryServiceName;
    }


    public String getPasswordRecoveryPath() {

        return passwordRecoveryPath;
    }


    public void setPasswordRecoveryPath(String passwordRecoveryPath) {

        this.passwordRecoveryPath = passwordRecoveryPath;
    }
}
