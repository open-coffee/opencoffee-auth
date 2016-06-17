package coffee.synyx.auth.security.config;

import coffee.synyx.auth.user.userdetails.SynyxUserDetailsContextMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ldap.core.support.LdapContextSource;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;


@Configuration
public class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private SynyxUserDetailsContextMapper synyxUserDetailsContextMapper;

    @Value(value = "${ldap.userSearchBase}")
    private String ldapUserSearchBase;

    @Value(value = "${ldap.userSearchFilter}")
    private String ldapUserSearchFilter;

    @Value(value = "${ldap.groupSearchBase}")
    private String ldapGroupSearchBase;

    @Value(value = "${ldap.groupSearchFilter}")
    private String ldapGroupSearchFilter;

    @Value(value = "${ldap.url}")
    private String ldapUrl;

    @Value(value = "${ldap.base}")
    private String ldapBase;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.ldapAuthentication()
            .userSearchBase(ldapUserSearchBase)
            .userSearchFilter(ldapUserSearchFilter)
            .groupSearchBase(ldapGroupSearchBase)
            .groupSearchFilter(ldapGroupSearchFilter)
            .contextSource(contextSource())
            .userDetailsContextMapper(synyxUserDetailsContextMapper);
    }


    @Bean
    public LdapContextSource contextSource() {

        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl(ldapUrl);
        contextSource.setBase(ldapBase);

        return contextSource;
    }
}
