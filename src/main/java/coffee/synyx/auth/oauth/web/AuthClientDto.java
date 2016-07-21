package coffee.synyx.auth.oauth.web;

/**
 * Dto of the {@link AuthClient}
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class AuthClientDto {

    private Long id;
    private String clientId;
    private String clientSecret;
    private String resourceIds;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedirectUri;
    private String authorities;
    private boolean accessTokenValidity;
    private boolean refreshTokenValidity;
    private String additionalInformation;
    private String autoapprove;

    public Long getId() {

        return id;
    }


    public void setId(Long id) {

        this.id = id;
    }


    public String getClientId() {

        return clientId;
    }


    public void setClientId(String clientId) {

        this.clientId = clientId;
    }


    public String getClientSecret() {

        return clientSecret;
    }


    public void setClientSecret(String clientSecret) {

        this.clientSecret = clientSecret;
    }


    public String getResourceIds() {

        return resourceIds;
    }


    public void setResourceIds(String resourceIds) {

        this.resourceIds = resourceIds;
    }


    public String getScope() {

        return scope;
    }


    public void setScope(String scope) {

        this.scope = scope;
    }


    public String getAuthorizedGrantTypes() {

        return authorizedGrantTypes;
    }


    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {

        this.authorizedGrantTypes = authorizedGrantTypes;
    }


    public String getWebServerRedirectUri() {

        return webServerRedirectUri;
    }


    public void setWebServerRedirectUri(String webServerRedirectUri) {

        this.webServerRedirectUri = webServerRedirectUri;
    }


    public String getAuthorities() {

        return authorities;
    }


    public void setAuthorities(String authorities) {

        this.authorities = authorities;
    }


    public boolean isAccessTokenValidity() {

        return accessTokenValidity;
    }


    public void setAccessTokenValidity(boolean accessTokenValidity) {

        this.accessTokenValidity = accessTokenValidity;
    }


    public boolean isRefreshTokenValidity() {

        return refreshTokenValidity;
    }


    public void setRefreshTokenValidity(boolean refreshTokenValidity) {

        this.refreshTokenValidity = refreshTokenValidity;
    }


    public String getAdditionalInformation() {

        return additionalInformation;
    }


    public void setAdditionalInformation(String additionalInformation) {

        this.additionalInformation = additionalInformation;
    }


    public String getAutoapprove() {

        return autoapprove;
    }


    public void setAutoapprove(String autoapprove) {

        this.autoapprove = autoapprove;
    }
}
