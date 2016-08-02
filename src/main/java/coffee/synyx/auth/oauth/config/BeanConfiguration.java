package coffee.synyx.auth.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
public class BeanConfiguration {

    private DataSource dataSource;

    @Autowired
    public BeanConfiguration(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Bean
    public TokenStore tokenStore() {

        return new JdbcTokenStore(dataSource);
    }


    @Bean
    public ApprovalStore approvalStore() {

        return new JdbcApprovalStore(dataSource);
    }


    @Bean
    public JdbcClientDetailsService JdbcClientDetailsService() {

        return new JdbcClientDetailsService(dataSource);
    }
}
