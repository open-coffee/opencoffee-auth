package coffee.synyx.auth.authorization.client.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class AuthClient implements ClientDetails {

    private static final long serialVersionUID = -2640708223216764662L;

    private String clientId;
    private String clientSecret;
    private Set<String> resourceIds = new HashSet<>();
    private Set<String> scope = new HashSet<>();
    private Set<String> authorizedGrantTypes = new HashSet<>();
    private Set<String> registeredRedirectUri = new HashSet<>();
    private Collection<GrantedAuthority> authorities = new HashSet<>();
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private boolean autoApprove;
    private Map<String, Object> additionalInformation = new HashMap<>();

    @Override
    public String getClientId() {

        return clientId;
    }


    public void setClientId(String clientId) {

        this.clientId = clientId;
    }


    @Override
    public Set<String> getResourceIds() {

        return this.resourceIds;
    }


    public void setResourceIds(Set<String> resourceIds) {

        this.resourceIds = resourceIds;
    }


    @Override
    public boolean isSecretRequired() {

        return clientSecret != null;
    }


    @Override
    public String getClientSecret() {

        return clientSecret;
    }


    public void setClientSecret(String clientSecret) {

        this.clientSecret = clientSecret;
    }


    @Override
    public boolean isScoped() {

        return scope != null && !scope.isEmpty();
    }


    @Override
    public Set<String> getScope() {

        return scope;
    }


    public void setScope(Set<String> scope) {

        this.scope = scope;
    }


    @Override
    public Set<String> getAuthorizedGrantTypes() {

        return authorizedGrantTypes;
    }


    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {

        this.authorizedGrantTypes = authorizedGrantTypes;
    }


    @Override
    public Set<String> getRegisteredRedirectUri() {

        return registeredRedirectUri;
    }


    public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {

        this.registeredRedirectUri = registeredRedirectUri;
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return authorities;
    }


    public void setAuthorities(Collection<GrantedAuthority> authorities) {

        this.authorities = authorities;
    }


    @Override
    public Integer getAccessTokenValiditySeconds() {

        return accessTokenValiditySeconds;
    }


    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {

        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }


    @Override
    public Integer getRefreshTokenValiditySeconds() {

        return refreshTokenValiditySeconds;
    }


    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {

        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }


    /**
     * We don't need autoApprove specific scopes. So this method just returns true if there is no need for approval on
     * any scope, or false if an approval is required.
     *
     * @param  scope  The scope for which an approval should be checked. Currently not used.
     *
     * @return  true if there is no need for approval on any scope, or false if an approval is required.
     */
    @Override
    public boolean isAutoApprove(String scope) {

        return autoApprove;
    }


    public void setAutoApprove(boolean autoApprove) {

        this.autoApprove = autoApprove;
    }


    @Override
    public Map<String, Object> getAdditionalInformation() {

        return additionalInformation;
    }


    public void setAdditionalInformation(Map<String, Object> additionalInformation) {

        this.additionalInformation = additionalInformation;
    }
}
