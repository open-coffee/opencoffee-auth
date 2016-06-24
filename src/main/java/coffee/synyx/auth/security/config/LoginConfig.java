package coffee.synyx.auth.security.config;

import coffee.synyx.auth.security.util.SecurityOrder;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


/**
 * TODO.
 */
@Configuration
@Order(SecurityOrder.OVERRIDE_DEFAULT_ORDER)
@EnableConfigurationProperties(ServerProperties.class)
public class LoginConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private ServerProperties serverProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception { // NOSONAR

        http.requestMatchers()
            .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access", "/logout")
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .logoutSuccessHandler(logoutSuccessHandler)
            .deleteCookies(serverProperties.getSession().getCookie().getName())
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated();
    }
}
