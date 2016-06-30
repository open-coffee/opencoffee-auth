package coffee.synyx.auth.user.controller;

import coffee.synyx.auth.user.SynyxAuthentication;

import org.slf4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Controller to provide OAuth2 user information.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@RestController
public class UserController {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    /**
     * Uses the current Principal to create a {@link SynyxAuthentication} and return this as representation of user
     * information.
     *
     * @param  user  The current user.
     *
     * @return  A representation of user information with {@link HttpStatus#OK}.
     */
    @RequestMapping("/user")
    public ResponseEntity<SynyxAuthentication> getUser(Principal user) {

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if ((user instanceof OAuth2Authentication) && ((OAuth2Authentication) user).isAuthenticated()) {
            LOGGER.debug("Requested Authentication for '{}'", user.getName());

            return new ResponseEntity<>(new SynyxAuthentication((OAuth2Authentication) user), HttpStatus.OK);
        } else {
            LOGGER.warn("Authentication of type {} was not expected. Expected: {}.", user.getClass().getName(),
                OAuth2Authentication.class.getName());

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
