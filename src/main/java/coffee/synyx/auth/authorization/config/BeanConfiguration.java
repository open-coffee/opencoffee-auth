package coffee.synyx.auth.authorization.config;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
public class BeanConfiguration {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final DataSource dataSource;

    @Autowired
    public BeanConfiguration(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Bean
    public TokenStore tokenStore() {

        LOGGER.info("//> JdbcTokenStore created");

        return new JdbcTokenStore(dataSource);
    }


    @Bean
    public ApprovalStore approvalStore() {

        LOGGER.info("//> JdbcApprovalStore created");

        return new JdbcApprovalStore(dataSource);
    }
}
