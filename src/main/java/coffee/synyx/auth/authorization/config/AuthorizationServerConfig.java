package coffee.synyx.auth.authorization.config;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Configures the authorization server.
 *
 * <ul>
 *   <li>
 *     {@link AuthorizationServerConfig#configure(AuthorizationServerSecurityConfigurer) Who has access to the token endpoint}
 *   </li>
 *   <li>
 *     {@link AuthorizationServerConfig#configure(AuthorizationServerEndpointsConfigurer) How Tokens and Approvals should be stored}
 *   </li>
 *   <li>
 *     {@link AuthorizationServerConfig#configure(ClientDetailsServiceConfigurer) Where client details could be found}
 *   </li>
 * </ul>
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;
    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter jwtTokenEnhancer;
    private final ApprovalStore approvalStore;

    @Autowired
    public AuthorizationServerConfig(AuthenticationManager authenticationManager, DataSource dataSource,
        TokenStore tokenStore, JwtAccessTokenConverter jwtTokenEnhancer, ApprovalStore approvalStore) {

        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.tokenStore = tokenStore;
        this.jwtTokenEnhancer = jwtTokenEnhancer;
        this.approvalStore = approvalStore;

        LOGGER.info("//> OAuth2AuthorizationServerConfig ...");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { // NOSONAR

        endpoints.tokenStore(tokenStore)
            .tokenEnhancer(jwtTokenEnhancer)
            .approvalStore(approvalStore)
            .authenticationManager(authenticationManager);

        LOGGER.info("//> Configure endpoints of tokenStore, approvalStore and authenticationManager");
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception { // NOSONAR

        clients.jdbc(dataSource);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
}
