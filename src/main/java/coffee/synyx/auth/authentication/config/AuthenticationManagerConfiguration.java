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
 * use ldap based on {@link LdapConfigurationProperties}.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
@EnableConfigurationProperties(LdapConfigurationProperties.class)
public class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private CoffeeNetUserDetailsContextMapper coffeeNetUserDetailsContextMapper;
    private LdapConfigurationProperties ldapConfigurationProperties;

    @Autowired
    public AuthenticationManagerConfiguration(CoffeeNetUserDetailsContextMapper coffeeNetUserDetailsContextMapper,
        LdapConfigurationProperties ldapConfigurationProperties) {

        this.coffeeNetUserDetailsContextMapper = coffeeNetUserDetailsContextMapper;
        this.ldapConfigurationProperties = ldapConfigurationProperties;

        LOGGER.info("//> AuthenticationManagerConfiguration...");
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.ldapAuthentication()
            .userSearchBase(ldapConfigurationProperties.getUserSearchBase())
            .userSearchFilter(ldapConfigurationProperties.getUserSearchFilter())
            .groupSearchBase(ldapConfigurationProperties.getGroupSearchBase())
            .groupSearchFilter(ldapConfigurationProperties.getGroupSearchFilter())
            .contextSource(contextSource())
            .userDetailsContextMapper(coffeeNetUserDetailsContextMapper);

        LOGGER.info("//> Initialize AuthenticationManagerBuilder with ldap authentication");
    }


    @Bean
    public LdapContextSource contextSource() {

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapConfigurationProperties.getUrl());
        contextSource.setBase(ldapConfigurationProperties.getBase());

        if (ldapConfigurationProperties.isConnectionWithTls()) {
            contextSource.setAuthenticationStrategy(new CoffeeNetDefaultTlsDirContextAuthenticationStrategy());
        }

        LOGGER.info("//> Created LdapContextSource for AuthenticationManagerBuilder");

        return contextSource;
    }
}
