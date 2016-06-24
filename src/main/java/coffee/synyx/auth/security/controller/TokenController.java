package coffee.synyx.auth.security.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;


/**
 * TODO.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@RestController
@RequestMapping(value = "/oauth/revoke")
public class TokenController {

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
    public DefaultTokenServices defaultTokenServices() {

        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());

        return defaultTokenServices;
    }


    @RequestMapping(method = RequestMethod.POST)
    public void revoke(@RequestBody String token) {

        defaultTokenServices().revokeToken(token);
    }
}
