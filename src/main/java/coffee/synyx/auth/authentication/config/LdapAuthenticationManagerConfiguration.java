package coffee.synyx.auth.authentication.config;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ldap.core.support.DefaultTlsDirContextAuthenticationStrategy;
import org.springframework.ldap.core.support.LdapContextSource;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;

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
public class LdapAuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final LdapCoffeeNetUserDetailsContextMapper ldapCoffeeNetUserDetailsContextMapper;
    private final LdapConfigurationProperties ldapConfigurationProperties;

    @Autowired
    public LdapAuthenticationManagerConfiguration(
        LdapCoffeeNetUserDetailsContextMapper ldapCoffeeNetUserDetailsContextMapper,
        LdapConfigurationProperties ldapConfigurationProperties) {

        this.ldapCoffeeNetUserDetailsContextMapper = ldapCoffeeNetUserDetailsContextMapper;
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
            .userDetailsContextMapper(ldapCoffeeNetUserDetailsContextMapper);

        LOGGER.info("//> Initialize AuthenticationManagerBuilder with ldap authentication");
    }


    @Bean
    public LdapUserDetailsService ldapUserDetailsService() {

        LdapContextSource contextSource = contextSource();
        String userSearchBase = ldapConfigurationProperties.getUserSearchBase();
        String userSearchFilter = ldapConfigurationProperties.getUserSearchFilter();
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(userSearchBase, userSearchFilter,
                contextSource);

        String groupSearchBase = ldapConfigurationProperties.getGroupSearchBase();
        DefaultLdapAuthoritiesPopulator authoritiesPopulator = new DefaultLdapAuthoritiesPopulator(contextSource,
                groupSearchBase);

        LdapUserDetailsService service = new LdapUserDetailsService(userSearch, authoritiesPopulator);
        service.setUserDetailsMapper(ldapCoffeeNetUserDetailsContextMapper);

        return service;
    }


    @Bean
    public LdapContextSource contextSource() {

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapConfigurationProperties.getUrl());
        contextSource.setBase(ldapConfigurationProperties.getBase());

        final String bindDn = ldapConfigurationProperties.getBindDn();
        final String bindPassword = ldapConfigurationProperties.getBindPassword();

        if (bindDn != null && bindPassword != null) {
            contextSource.setUserDn(bindDn);
            contextSource.setPassword(bindPassword);
        }

        if (ldapConfigurationProperties.isConnectionWithTls()) {
            contextSource.setAuthenticationStrategy(new DefaultTlsDirContextAuthenticationStrategy());
        }

        LOGGER.info("//> Created LdapContextSource for AuthenticationManagerBuilder");

        return contextSource;
    }
}
