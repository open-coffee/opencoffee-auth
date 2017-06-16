package coffee.synyx.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * The CoffeeNet Authentication Server.
 *
 * <p>OAuth2 based authentication against ldap</p>
 *
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
public class AuthServerApplication {

    AuthServerApplication() {

        // hide implicit public constructor
    }

    @SuppressWarnings("checkstyle:uncommentedmain")
    public static void main(String[] args) {

        SpringApplication.run(AuthServerApplication.class, args);
    }
}
