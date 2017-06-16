package coffee.synyx.auth.authorization.client.config;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
public class ClientStoreConfiguration {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private DataSource dataSource;

    @Autowired
    public ClientStoreConfiguration(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {

        LOGGER.info("//> JdbcClientDetailsService created");

        return new JdbcClientDetailsService(dataSource);
    }
}
