package coffee.synyx.auth.oauth.web;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Entity
@Table(name = "oauth_client_details")
public class AuthClient {

    @Id
    @GeneratedValue
    private Long id;

    @Length(min = 1, max = 200)
    private String clientId;

    @Length(min = 1, max = 256)
    private String clientSecret;

    @Length(min = 1, max = 256)
    private String resourceIds;

    @Length(min = 1, max = 256)
    private String scope;

    @Length(min = 1, max = 256)
    private String authorizedGrantTypes;

    @Length(min = 1, max = 256)
    private String webServerRedirectUri;

    @Length(min = 1, max = 256)
    private String authorities;

    private boolean accessTokenValidity;

    private boolean refreshTokenValidity;

    @Length(min = 1, max = 4096)
    private String additionalInformation;

    @Length(min = 1, max = 256)
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
