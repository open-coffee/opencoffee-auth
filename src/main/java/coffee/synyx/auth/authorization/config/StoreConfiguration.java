package coffee.synyx.auth.authorization.config;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.ClassPathResource;

import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
@EnableConfigurationProperties(AuthorizationProperties.class)
public class StoreConfiguration {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final DataSource dataSource;
    private final AuthorizationProperties authorizationProperties;

    @Autowired
    public StoreConfiguration(DataSource dataSource, AuthorizationProperties authorizationProperties) {

        this.dataSource = dataSource;
        this.authorizationProperties = authorizationProperties;
    }

    @Bean
    public TokenStore tokenStore() {

        LOGGER.info("//> JdbcTokenStore created");

        return new JwtTokenStore(jwtTokenEnhancer());
    }


    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {

        ClassPathResource jksPath = new ClassPathResource(authorizationProperties.getJksPath());
        char[] jksPassword = authorizationProperties.getJksPassword().toCharArray();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(jksPath, jksPassword);

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(authorizationProperties.getJksAlias()));

        return converter;
    }


    @Bean
    public ApprovalStore approvalStore() {

        LOGGER.info("//> JdbcApprovalStore created");

        return new JdbcApprovalStore(dataSource);
    }
}
