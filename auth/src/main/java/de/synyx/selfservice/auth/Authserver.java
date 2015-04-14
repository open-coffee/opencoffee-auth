package de.synyx.selfservice.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by klem on 13.04.15.
 */
@SpringBootApplication //NOSONAR
@EnableResourceServer
public class Authserver {

    /**This main method starts the Authserver
     *
     * @param args are passed to SpringApplication.run()
     */
    public static void main(String[] args) { //NOSONAR
        SpringApplication.run(Authserver.class, args);
    }
}
