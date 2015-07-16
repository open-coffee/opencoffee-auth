package de.synyx.selfservice.auth.security.config;

import de.synyx.selfservice.auth.user.userdetails.SynyxUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ApprovalStore approvalStore;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccessTokenConverter accessTokenConverter;

    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { // NOSONAR

        endpoints.tokenStore(tokenStore)
            .approvalStore(approvalStore)
            .authenticationManager(authenticationManager)
            .accessTokenConverter(accessTokenConverter);
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception { // NOSONAR

        clients.jdbc(dataSource);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }


    @Bean
    public AccessTokenConverter accessTokenConverter() {

        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter());

        return defaultAccessTokenConverter;
    }


    public UserAuthenticationConverter userAuthenticationConverter() {

        return new DefaultUserAuthenticationConverter() {

            @Override
            public Map<String, ?> convertUserAuthentication(Authentication authentication) {

                HashMap<String, Object> response = new HashMap<>();

                response.putAll(super.convertUserAuthentication(authentication));

                SynyxUserDetails synyxUserDetails = (SynyxUserDetails) authentication.getPrincipal();
                response.put("mail", synyxUserDetails.getMail());

                return response;
            }
        };
    }
}
