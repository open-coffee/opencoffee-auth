package coffee.synyx.auth.config.security;

import coffee.synyx.auth.config.security.ldap.CoffeeDefaultTlsDirContextAuthenticationStrategy;
import coffee.synyx.auth.config.security.ldap.LdapConfigurationProperties;
import coffee.synyx.auth.oauth.user.service.SynyxUserDetailsContextMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ldap.core.support.LdapContextSource;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;


/**
 * Configures an {@link org.springframework.security.authentication.AuthenticationManager authentication manager} to use
 * ldap based on {@link LdapConfigurationProperties}.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
@EnableConfigurationProperties(LdapConfigurationProperties.class)
public class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private SynyxUserDetailsContextMapper synyxUserDetailsContextMapper;
    private LdapConfigurationProperties ldapConfigurationProperties;

    @Autowired
    public AuthenticationManagerConfiguration(SynyxUserDetailsContextMapper synyxUserDetailsContextMapper,
        LdapConfigurationProperties ldapConfigurationProperties) {

        this.synyxUserDetailsContextMapper = synyxUserDetailsContextMapper;
        this.ldapConfigurationProperties = ldapConfigurationProperties;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.ldapAuthentication()
            .userSearchBase(ldapConfigurationProperties.getUserSearchBase())
            .userSearchFilter(ldapConfigurationProperties.getUserSearchFilter())
            .groupSearchBase(ldapConfigurationProperties.getGroupSearchBase())
            .groupSearchFilter(ldapConfigurationProperties.getGroupSearchFilter())
            .contextSource(contextSource())
            .userDetailsContextMapper(synyxUserDetailsContextMapper);
    }


    @Bean
    public LdapContextSource contextSource() {

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapConfigurationProperties.getUrl());
        contextSource.setBase(ldapConfigurationProperties.getBase());
        contextSource.setAuthenticationStrategy(new CoffeeDefaultTlsDirContextAuthenticationStrategy());

        return contextSource;
    }
}
