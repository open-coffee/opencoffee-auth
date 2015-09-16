package de.synyx.selfservice.auth.security.config;

import de.synyx.selfservice.auth.security.util.SecurityOrder;

import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@Order(SecurityOrder.OVERRIDE_DEFAULT_ORDER)
public class LoginConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception { // NOSONAR

        http.formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .deleteCookies("JSESSIONID")
            .and()
            .requestMatchers()
            .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated();
    }
}
