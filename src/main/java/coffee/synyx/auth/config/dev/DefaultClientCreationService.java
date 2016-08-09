package coffee.synyx.auth.config.dev;

import coffee.synyx.auth.oauth.web.AuthClient;

import org.slf4j.Logger;

import org.springframework.context.annotation.DependsOn;

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.annotation.PostConstruct;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@DependsOn("liquibase")
public class DefaultClientCreationService {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());
    private final JdbcClientDetailsService jdbcClientDetailsService;

    public DefaultClientCreationService(JdbcClientDetailsService jdbcClientDetailsService) {

        this.jdbcClientDetailsService = jdbcClientDetailsService;
    }

    @PostConstruct
    public void createDefaultClient() {

        LOGGER.info("Adding default OAuth Client: testClient/testClientSecret");

        AuthClient authClient = new AuthClient();
        authClient.setAutoApprove(true);
        authClient.setClientId("testClient");
        authClient.setClientSecret("testClientSecret");
        authClient.getScope().add("openid");
        jdbcClientDetailsService.addClientDetails(authClient);
    }
}
