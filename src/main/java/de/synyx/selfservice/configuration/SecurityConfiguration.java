package de.synyx.selfservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Created by klem on 18.02.15.
 */

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value(value = "${ldap.hostUrl}")
    private String ldapHostUrl;

    @Autowired
    BasicLogoutSuccessHandler basicLogoutSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        /*auth.ldapAuthentication().contextSource().url(ldapHostUrl).and().
                userDnPatterns("uid={0},ou=people").groupSearchBase("ou=group").
                groupSearchFilter("memberUid={1}").userSearchBase("ou=people"); */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //TODO:nur während der Entwicklung
                .authorizeRequests().anyRequest().authenticated()
                .and().httpBasic()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).deleteCookies("JSESSIONID")
                .logoutSuccessHandler(basicLogoutSuccessHandler).permitAll();
    }
}
