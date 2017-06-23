package coffee.synyx.auth.resource.user;

import coffee.synyx.auth.authentication.config.CoffeeNetUserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


/**
 * Representation of an authentication of the Coffeenet and will be returned as 'user information'.
 *
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
final class CoffeeNetAuthentication {

    private final String id;
    private final String name;

    @JsonInclude(NON_NULL)
    private final String email;
    private final boolean clientOnly;

    /**
     * This should either be a String (in case of clientOnly authentication) or an instance of
     * {@link CoffeeNetUserDetails} (in case of user authentication).
     */
    private final Object principal;

    CoffeeNetAuthentication(@NotNull String id, @NotNull String name, String email, @NotNull boolean clientOnly,
        @NotNull Object principal) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.clientOnly = clientOnly;
        this.principal = principal;
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


    public String getEmail() {

        return email;
    }
}
