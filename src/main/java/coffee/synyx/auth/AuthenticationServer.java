package coffee.synyx.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@SuppressWarnings("squid:HideUtilityClassConstructor")
public class AuthenticationServer {

    @SuppressWarnings("checkstyle:uncommentedmain")
    public static void main(String[] args) {

        SpringApplication.run(AuthenticationServer.class, args);
    }
}
