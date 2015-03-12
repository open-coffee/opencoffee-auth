package de.synyx.selfservice.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableOAuth2Resource
public class ResourceApplication {

    public static final String MODULES_SCRIPTS_ENDPOINT = "/scripts";
    public static final String EVENT_LISTENER_PROCESSOR_CONTROLLER_ENDPOINT = "/EventListenerProcessor";

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(ResourceApplication.class, args);
    }
}