package de.synyx.exampleModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.ApplicationContext;

/**
 * Created by Yannic on 27.02.2015.
 */
@SpringBootApplication
@EnableOAuth2Resource
public class ExampleModule {

    public static void main(String[] args){
        ApplicationContext ctx = SpringApplication.run(ExampleModule.class, args);
    }

}
