package coffee.synyx.auth;

import org.h2.tools.Server;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.sql.SQLException;


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
public class AuthenticationServer {

    @SuppressWarnings("checkstyle:uncommentedmain")
    public static void main(String[] args) {

        SpringApplication.run(AuthenticationServer.class, args);
    }


    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer(@Value("${h2.consolePort}") String h2ConsolePort) throws SQLException {

        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", h2ConsolePort);
    }
}
