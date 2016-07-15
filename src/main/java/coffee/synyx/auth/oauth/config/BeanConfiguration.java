package coffee.synyx.auth.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
public class BeanConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {

        return new JdbcTokenStore(dataSource);
    }


    @Bean
    public ApprovalStore approvalStore() {

        return new JdbcApprovalStore(dataSource);
    }


    @Bean
    public ConsumerTokenServices tokenServices() {

        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());

        return defaultTokenServices;
    }
}
