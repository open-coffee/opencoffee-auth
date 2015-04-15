package de.synyx.selfservice.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Yannic Klem - klem@synyx.de
 */
@SpringBootApplication //NOSONAR
@EnableResourceServer
public class Authserver {



    public static void main(String[] args) { //NOSONAR
        SpringApplication.run(Authserver.class, args);
    }
}
