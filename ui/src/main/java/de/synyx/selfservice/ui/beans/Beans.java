package de.synyx.selfservice.ui.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by klem on 13.04.15.
 */
@Configuration
public class Beans {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /*
    @Bean
    org.h2.tools.Server h2Server() {
        Server server = new Server();
        try {
            server.runTool("-tcp");
            server.runTool("-tcpAllowOthers");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return server;

    }
    */
}
