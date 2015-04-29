package de.synyx.selfservice.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;

/**
 * Created by klem on 14.04.15.
 */
@SpringBootApplication //NOSONAR
@EnableOAuth2Resource
public class ExampleModule {

    public static void main(String[] args){ //NOSONAR
        SpringApplication.run(ExampleModule.class, args);
    }
}
