package de.synyx.selfservice.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
@EnableZuulProxy
public class UiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}

