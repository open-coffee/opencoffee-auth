package coffee.synyx.auth.config.security;

import coffee.synyx.auth.config.AuthServerConfigurationProperties;
import coffee.synyx.auth.oauth.config.OAuth2ResourceServerConfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;


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
@EnableConfigurationProperties({ ServerProperties.class, AuthServerConfigurationProperties.class })
public class LoginConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGOUT = "/logout";
    private static final String ADMIN_ROLE = "ROLE_COFFEENET-ADMIN";

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private AuthServerConfigurationProperties authServerConfigurationProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception { // NOSONAR

        http.requestMatchers()
            .antMatchers("/", "/login", "/oauth/authorize", "/oauth/confirm_access", LOGOUT, "/h2-console/**",
                    "/webjars/**", "/health", "/clients/**")
            .and()
            .formLogin()
            .defaultSuccessUrl(authServerConfigurationProperties.getDefaultRedirectUrl())
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .logoutUrl(LOGOUT)
            .logoutSuccessHandler(logoutSuccessHandler)
            .deleteCookies(serverProperties.getSession().getCookie().getName())
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, LOGOUT)
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers("/h2-console/**")
            .permitAll()
            .and()
            .headers()
            .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"))
            .frameOptions()
            .disable()
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/webjars/**", "/health")
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers("/clients/new", "/clients/edit", "/clients/delete")
            .hasAuthority(ADMIN_ROLE)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/clients/**")
            .authenticated()
            .antMatchers("/clients/**")
            .hasAuthority(ADMIN_ROLE)
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new DefaultAccessDeniedHandler(authServerConfigurationProperties));
    }
}
