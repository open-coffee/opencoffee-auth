package coffee.synyx.auth.user;

import coffee.synyx.auth.user.userdetails.SynyxUserDetails;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.validation.constraints.NotNull;


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
