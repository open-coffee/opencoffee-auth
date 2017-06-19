package coffee.synyx.auth.authorization.config;

import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;


/**
 * @author  Tobias Schneider
 */
@Validated
@ConfigurationProperties(prefix = "auth.keystore")
public class KeyStoreProperties {

    private boolean enabled = true;

    private String jksPassword;

    @NotEmpty
    private String jksAlias = "coffeenet";

    @NotEmpty
    private String jksPath = "file:coffeenet.jks";

    public boolean isEnabled() {

        return enabled;
    }


    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
    }


    public String getJksPassword() {

        return jksPassword;
    }


    public void setJksPassword(String jksPassword) {

        this.jksPassword = jksPassword;
    }


    public String getJksAlias() {

        return jksAlias;
    }


    public void setJksAlias(String jksAlias) {

        this.jksAlias = jksAlias;
    }


    public String getJksPath() {

        return jksPath;
    }


    public void setJksPath(String jksPath) {

        this.jksPath = jksPath;
    }
}
