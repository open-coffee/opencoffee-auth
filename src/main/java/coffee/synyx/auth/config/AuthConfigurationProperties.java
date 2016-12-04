package coffee.synyx.auth.config;

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
}
