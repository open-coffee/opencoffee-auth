package coffee.synyx.auth.oauth.user.api;

import coffee.synyx.auth.oauth.user.service.CoffeeNetUserDetails;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.validation.constraints.NotNull;


/**
 * Representation of an authentication. This Object will be returned as user information.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
final class CoffeeNetAuthentication {

    private final String id;
    private final String name;
    private final boolean clientOnly;

    /**
     * This should either be a String (in case of clientOnly authentication) or an instance of
     * {@link CoffeeNetUserDetails} (in case of user authentication).
     */
    private final Object principal;

    CoffeeNetAuthentication(@NotNull OAuth2Authentication oAuth2Authentication) {

        clientOnly = oAuth2Authentication.isClientOnly();

        if (clientOnly) {
            this.principal = oAuth2Authentication.getName();
            this.id = oAuth2Authentication.getName();
            this.name = oAuth2Authentication.getName();
        } else {
            CoffeeNetUserDetails coffeeNetUserDetails = (CoffeeNetUserDetails) oAuth2Authentication.getPrincipal();
            String username = coffeeNetUserDetails.getUsername();

            this.principal = new CoffeeNetAuthenticationDetails(coffeeNetUserDetails.getMail(),
                    coffeeNetUserDetails.getAuthorities(), username);
            this.id = username;
            this.name = username;
        }
    }

    public String getId() {

        return id;
    }


    public String getName() {

        return name;
    }


    public Object getPrincipal() {

        return principal;
    }


    public boolean isClientOnly() {

        return clientOnly;
    }
}
