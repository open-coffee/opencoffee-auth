package coffee.synyx.auth.oauth.config;

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

import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Configures the authorization server.
 *
 * <ul>
 *   <li>
 *     {@link OAuth2AuthorizationServerConfig#configure(AuthorizationServerSecurityConfigurer) Who has access to the token endpoint}
 *   </li>
 *   <li>
 *     {@link OAuth2AuthorizationServerConfig#configure(AuthorizationServerEndpointsConfigurer) How Tokens and Approvals should be stored}
 *   </li>
 *   <li>
 *     {@link OAuth2AuthorizationServerConfig#configure(ClientDetailsServiceConfigurer) Where client details could be found}
 *   </li>
 * </ul>
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private AuthenticationManager authenticationManager;
    private DataSource dataSource;
    private TokenStore tokenStore;
    private ApprovalStore approvalStore;

    @Autowired
    public OAuth2AuthorizationServerConfig(AuthenticationManager authenticationManager, DataSource dataSource,
        TokenStore tokenStore, ApprovalStore approvalStore) {

        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.tokenStore = tokenStore;
        this.approvalStore = approvalStore;

        LOGGER.info("//> OAuth2AuthorizationServerConfig ...");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception { // NOSONAR

        endpoints.tokenStore(tokenStore).approvalStore(approvalStore).authenticationManager(authenticationManager);

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
