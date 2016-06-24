package coffee.synyx.auth.security.config;

import coffee.synyx.auth.user.userdetails.SynyxUserDetailsContextMapper;

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

    @Autowired
    private SynyxUserDetailsContextMapper synyxUserDetailsContextMapper;

    @Autowired
    private LdapConfigurationProperties ldapConfigurationProperties;

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

        return contextSource;
    }
}
