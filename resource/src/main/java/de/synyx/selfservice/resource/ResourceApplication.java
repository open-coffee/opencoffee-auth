package de.synyx.selfservice.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;

@SpringBootApplication
@EnableRedisHttpSession
public class ResourceApplication {

    public static final String MODULES_SCRIPTS_ENDPOINT = "/scripts";
    public static final String EVENT_LISTENER_PROCESSOR_CONTROLLER_ENDPOINT = "/EventListenerProcessor";

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(ResourceApplication.class, args);
    }

    @Bean
    HeaderHttpSessionStrategy sessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

}