package de.synyx;

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
/*
        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
*/
    }

}