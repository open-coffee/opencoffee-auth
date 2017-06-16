package coffee.synyx.auth.authentication.config;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ldap.core.support.LdapContextSource;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Configures an {@link org.springframework.security.authentication.AuthenticationManager authentication manager} to
 * use ldap based on {@link AuthLdapConfigurationProperties}.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
@EnableConfigurationProperties(AuthLdapConfigurationProperties.class)
public class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private CoffeeNetUserDetailsContextMapper coffeeNetUserDetailsContextMapper;
    private AuthLdapConfigurationProperties authLdapConfigurationProperties;

    @Autowired
    public AuthenticationManagerConfiguration(CoffeeNetUserDetailsContextMapper coffeeNetUserDetailsContextMapper,
        AuthLdapConfigurationProperties authLdapConfigurationProperties) {

        this.coffeeNetUserDetailsContextMapper = coffeeNetUserDetailsContextMapper;
        this.authLdapConfigurationProperties = authLdapConfigurationProperties;

        LOGGER.info("//> AuthenticationManagerConfiguration...");
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.ldapAuthentication()
            .userSearchBase(authLdapConfigurationProperties.getUserSearchBase())
            .userSearchFilter(authLdapConfigurationProperties.getUserSearchFilter())
            .groupSearchBase(authLdapConfigurationProperties.getGroupSearchBase())
            .groupSearchFilter(authLdapConfigurationProperties.getGroupSearchFilter())
            .contextSource(contextSource())
            .userDetailsContextMapper(coffeeNetUserDetailsContextMapper);

        LOGGER.info("//> Initialize AuthenticationManagerBuilder with ldap authentication");
    }


    @Bean
    public LdapContextSource contextSource() {

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(authLdapConfigurationProperties.getUrl());
        contextSource.setBase(authLdapConfigurationProperties.getBase());

        if (authLdapConfigurationProperties.isConnectionWithTls()) {
            contextSource.setAuthenticationStrategy(new CoffeeNetDefaultTlsDirContextAuthenticationStrategy());
        }

        LOGGER.info("//> Created LdapContextSource for AuthenticationManagerBuilder");

        return contextSource;
    }
}
