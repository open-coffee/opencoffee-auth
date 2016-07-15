package coffee.synyx.auth.oauth.user.api;

import coffee.synyx.auth.oauth.user.service.SynyxUserDetails;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.validation.constraints.NotNull;


/**
 * Representation of an authentication. This Object will be returned as user information. It temporary extends
 * {@link OAuth2Authentication} due to backwards compatibility. This will be removed in future releases.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
public class SynyxAuthentication extends OAuth2Authentication {

    private final String id;
    private final String name;
    private final boolean clientOnly;
    private final Object principal;

    public SynyxAuthentication(@NotNull OAuth2Authentication oAuth2Authentication) {

        super(oAuth2Authentication.getOAuth2Request(), oAuth2Authentication.getUserAuthentication());

        clientOnly = oAuth2Authentication.isClientOnly();

        if (clientOnly) {
            this.principal = oAuth2Authentication.getName();
            this.id = oAuth2Authentication.getName();
            this.name = oAuth2Authentication.getName();
        } else {
            this.principal = oAuth2Authentication.getPrincipal();
            this.id = ((SynyxUserDetails) principal).getUsername();
            this.name = ((SynyxUserDetails) principal).getUsername();
        }
    }

    public String getId() {

        return id;
    }


    @Override
    public String getName() {

        return name;
    }


    @Override
    public Object getPrincipal() {

        return principal;
    }


    @Override
    public boolean isClientOnly() {

        return clientOnly;
    }
}
