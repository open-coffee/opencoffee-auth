package de.synyx.selfservice.serviceRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by klem on 05.05.15.
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistry {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistry.class, args);
    }
}
