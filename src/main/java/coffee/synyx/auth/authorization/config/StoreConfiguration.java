package coffee.synyx.auth.authorization.config;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.ClassPathResource;

import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
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
class StoreConfiguration {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final DataSource dataSource;
    private final AuthorizationProperties authorizationProperties;

    @Autowired
    StoreConfiguration(DataSource dataSource, AuthorizationProperties authorizationProperties) {

        this.dataSource = dataSource;
        this.authorizationProperties = authorizationProperties;
    }

    @Bean
    @Autowired
    TokenStore tokenStore(JwtAccessTokenConverter accessTokenConverter) {

        LOGGER.info("//> JwtTokenStore created");

        return new JwtTokenStore(accessTokenConverter);
    }


    @Bean
    @Autowired
    JwtAccessTokenConverter accessTokenConverter(UserAuthenticationConverter userAuthenticationConverter) {

        final ClassPathResource jksPath = new ClassPathResource(authorizationProperties.getJksPath());
        final char[] jksPassword = authorizationProperties.getJksPassword().toCharArray();
        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(jksPath, jksPassword);

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(authorizationProperties.getJksAlias()));

        ((DefaultAccessTokenConverter) converter.getAccessTokenConverter()).setUserTokenConverter(
            userAuthenticationConverter);

        return converter;
    }


    @Bean
    @Autowired
    UserAuthenticationConverter userAuthenticationConverter(LdapUserDetailsService ldapUserDetailsService) {

        DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(ldapUserDetailsService);

        return converter;
    }


    @Bean
    ApprovalStore approvalStore() {

        LOGGER.info("//> JdbcApprovalStore created");

        return new JdbcApprovalStore(dataSource);
    }
}
