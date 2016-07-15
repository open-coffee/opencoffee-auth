package coffee.synyx.auth.oauth.token.api;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;


/**
 * TODO.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@RestController
public class TokenController {

    private final ConsumerTokenServices tokenServices;

    @Autowired
    public TokenController(ConsumerTokenServices tokenServices) {

        this.tokenServices = tokenServices;
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/token/{token}", method = DELETE)
    public void revoke(@PathVariable("token") String token) {

        tokenServices.revokeToken(token);
    }
}
