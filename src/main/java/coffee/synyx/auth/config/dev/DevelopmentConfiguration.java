package coffee.synyx.auth.config.dev;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@ConfigurationProperties(prefix = "coffeenet")
public class DevelopmentConfiguration {

    private boolean development;

    public boolean isDevelopment() {

        return development;
    }


    public void setDevelopment(boolean development) {

        this.development = development;
    }
}
