package coffee.synyx.auth.authorization.config;

import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;


/**
 * @author  Tobias Schneider
 */
@Validated
@ConfigurationProperties(prefix = "auth.key")
public class AuthorizationProperties {

    @NotEmpty
    private String jksPassword;

    @NotEmpty
    private String jksAlias = "coffeenet";

    @NotEmpty
    private String jksPath = "coffeenet.jks";

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
