package coffee.synyx.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@ConfigurationProperties(prefix = "auth")
public class AuthServerConfigurationProperties {

    private String defaultRedirectUrl = "https://synyx.coffee";

    public String getDefaultRedirectUrl() {

        return defaultRedirectUrl;
    }


    public void setDefaultRedirectUrl(String defaultRedirectUrl) {

        this.defaultRedirectUrl = defaultRedirectUrl;
    }
}
