package coffee.synyx.auth.resource.user;

import coffee.synyx.auth.authentication.config.CoffeeNetUserDetails;

import org.slf4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Controller to provide OAuth2 user information.
 *
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RestController
public class UserController {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    /**
     * Uses the current Principal to create a {@link CoffeeNetAuthentication} and return this as representation of user
     * information.
     *
     * @param  user  The current user.
     *
     * @return  A representation of user information with {@link HttpStatus#OK}.
     */
    @RequestMapping("/user")
    public ResponseEntity<CoffeeNetAuthentication> getUser(OAuth2Authentication user) {

        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        ResponseEntity<CoffeeNetAuthentication> responseEntity;

        if (user.isAuthenticated()) {
            LOGGER.info("//> Requested Authentication for '{}'", user.getName());

            CoffeeNetAuthentication coffeeNetAuthentication = mapToCoffeeNetAuthentication(user);

            responseEntity = new ResponseEntity<>(coffeeNetAuthentication, OK);
        } else {
            LOGGER.warn("//> Authentication of type {} was not expected. Expected: {}.", user.getClass().getName(),
                OAuth2Authentication.class.getName());

            responseEntity = new ResponseEntity<>(UNAUTHORIZED);
        }

        return responseEntity;
    }


    private CoffeeNetAuthentication mapToCoffeeNetAuthentication(OAuth2Authentication oAuth2Authentication) {

        final CoffeeNetAuthentication coffeeNetAuthentication;

        if (oAuth2Authentication.isClientOnly()) {
            String name = oAuth2Authentication.getName();
            coffeeNetAuthentication = new CoffeeNetAuthentication(name, name, null, true, name);
        } else {
            CoffeeNetUserDetails coffeeNetUserDetails = (CoffeeNetUserDetails) oAuth2Authentication.getPrincipal();
            String username = coffeeNetUserDetails.getUsername();
            Collection<? extends GrantedAuthority> authorities = coffeeNetUserDetails.getAuthorities();
            String email = coffeeNetUserDetails.getMail();

            CoffeeNetAuthenticationDetails details = new CoffeeNetAuthenticationDetails(email, authorities, username);

            coffeeNetAuthentication = new CoffeeNetAuthentication(username, username, email, false, details);
        }

        return coffeeNetAuthentication;
    }
}
