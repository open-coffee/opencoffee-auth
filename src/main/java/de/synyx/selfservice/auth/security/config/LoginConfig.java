package de.synyx.selfservice.auth.security.config;

import de.synyx.selfservice.auth.security.util.SecurityOrder;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


@Configuration
@Order(SecurityOrder.OVERRIDE_DEFAULT_ORDER)
public class LoginConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception { // NOSONAR

        http.formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .logoutSuccessHandler(logoutSuccessHandler)
            .deleteCookies("JSESSIONID")
            .and()
            .requestMatchers()
            .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access", "/logout")
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated();
    }
}
