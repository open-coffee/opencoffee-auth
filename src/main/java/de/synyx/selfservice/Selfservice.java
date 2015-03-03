package de.synyx.selfservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Selfservice {

    public static final String MODULES_EVENT_ENDPOINT = "/events";
    public static final String EVENT_LISTENER_PROCESSOR_CONTROLLER_ENDPOINT = "/EventListenerProcessor";

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Selfservice.class, args);
    }

}