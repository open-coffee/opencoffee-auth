package coffee.synyx.auth.authorization.config;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.Resource;

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
@EnableConfigurationProperties(KeyStoreProperties.class)
class StoreConfiguration {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final DataSource dataSource;
    private final KeyStoreProperties keyStoreProperties;
    private final ApplicationContext context;

    @Autowired
    StoreConfiguration(DataSource dataSource, KeyStoreProperties keyStoreProperties, ApplicationContext context) {

        this.dataSource = dataSource;
        this.keyStoreProperties = keyStoreProperties;
        this.context = context;
    }

    @Bean
    @Autowired
    TokenStore tokenStore(JwtAccessTokenConverter accessTokenConverter) {

        final JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter);
        LOGGER.info("//> JwtTokenStore created");

        return jwtTokenStore;
    }


    @Bean
    @Autowired
    JwtAccessTokenConverter accessTokenConverter(UserAuthenticationConverter userAuthenticationConverter) {

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        if (keyStoreProperties.isEnabled()) {
            final Resource jksPath = context.getResource(keyStoreProperties.getJksPath());

            String password = keyStoreProperties.getJksPassword();
            final char[] jksPassword = (password == null) ? null : password.toCharArray();

            final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(jksPath, jksPassword);
            converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyStoreProperties.getJksAlias()));
        }

        ((DefaultAccessTokenConverter) converter.getAccessTokenConverter()).setUserTokenConverter(
            userAuthenticationConverter);

        LOGGER.info("//> JwtAccessTokenConverter created");

        return converter;
    }


    @Bean
    @Autowired
    UserAuthenticationConverter userAuthenticationConverter(LdapUserDetailsService ldapUserDetailsService) {

        final DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(ldapUserDetailsService);

        LOGGER.info("//> UserAuthenticationConverter created");

        return converter;
    }


    @Bean
    ApprovalStore approvalStore() {

        final JdbcApprovalStore jdbcApprovalStore = new JdbcApprovalStore(dataSource);
        LOGGER.info("//> JdbcApprovalStore created");

        return jdbcApprovalStore;
    }
}
