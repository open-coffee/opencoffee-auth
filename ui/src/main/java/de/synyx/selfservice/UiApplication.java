package de.synyx.selfservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso;

@SpringBootApplication //NOSONAR
@EnableOAuth2Sso
public class UiApplication {

    /**This main method starts the UiApplication
     *
     * @param args are passed to SpringApplication.run()
     */
    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}
