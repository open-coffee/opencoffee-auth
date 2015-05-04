package de.synyx.selfservice.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Yannic Klem - klem@synyx.de
 */
@SpringBootApplication //NOSONAR
public class Authserver {
    public static void main(String[] args) { //NOSONAR
        SpringApplication.run(Authserver.class, args);
    }
}
