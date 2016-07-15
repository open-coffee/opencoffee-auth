package coffee.synyx.auth.config.security;

import coffee.synyx.auth.oauth.config.OAuth2ResourceServerConfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


/**
 * This configuration configures a form based login for all paths defined in the first ant matcher. It is required for
 * the "authorization_code" grant type flow.
 *
 * <p>Every path NOT matched by this configuration, will be handled by {@link OAuth2ResourceServerConfig}</p>
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
@Order(SecurityProperties.DEFAULT_FILTER_ORDER - 2)
@EnableConfigurationProperties(ServerProperties.class)
public class LoginConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private ServerProperties serverProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception { // NOSONAR

        http.requestMatchers()
            .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access", "/logout", "/h2-console/**")
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
