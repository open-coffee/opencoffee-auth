package coffee.synyx.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Configuration properties of the {@code AuthenticationServer}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Yannic Klem - klem@synyx.de
 */
@ConfigurationProperties(prefix = "auth")
public class AuthServerConfigurationProperties {

    private boolean development = true;

    private String defaultRedirectUrl = "localhost:8080";

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
