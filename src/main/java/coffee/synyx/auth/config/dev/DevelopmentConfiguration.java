package coffee.synyx.auth.config.dev;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
@DependsOn("liquibase")
@ConfigurationProperties(prefix = "coffeenet")
@ConditionalOnProperty(prefix = "coffeenet", name = "development", havingValue = "true", matchIfMissing = true)
public class DevelopmentConfiguration {

    private boolean development;

    public boolean isDevelopment() {

        return development;
    }


    public void setDevelopment(boolean development) {

        this.development = development;
    }


    @Bean
    public DefaultClientCreationService defaultClientCreationService(JdbcClientDetailsService clientDetailsService) {

        return new DefaultClientCreationService(clientDetailsService);
    }
}
