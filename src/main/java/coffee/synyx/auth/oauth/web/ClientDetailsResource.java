package coffee.synyx.auth.oauth.web;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dto of the {@link ClientDetails}
 *
 * @author  Yannic Klem - klem@synyx.de
 */
public class ClientDetailsResource {

    @Length(min = 1, max = 200)
    private String clientId;

    @Length(min = 8, max = 256)
    private String clientSecret;

    private String resourceIds;
    private String scope;
    private String authorizedGrantTypes;

    @Length(min = 1, max = 256)
    private String registeredRedirectUri;

    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private boolean autoApprove;
    private Map<String, Object> additionalInformation = new HashMap<>();

    public ClientDetailsResource() {

    }

    public ClientDetailsResource(ClientDetails clientDetails) {

        this.clientId = clientDetails.getClientId();
        this.clientSecret = clientDetails.getClientSecret();
        this.resourceIds = StringUtils.collectionToCommaDelimitedString(clientDetails.getResourceIds());
        this.scope = StringUtils.collectionToCommaDelimitedString(clientDetails.getScope());
        this.authorizedGrantTypes = StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorizedGrantTypes());
        this.registeredRedirectUri = StringUtils.collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri());
        this.authorities = StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorities());
        this.accessTokenValidity = clientDetails.getAccessTokenValiditySeconds();
        this.refreshTokenValidity = clientDetails.getRefreshTokenValiditySeconds();
        this.additionalInformation = clientDetails.getAdditionalInformation();
    }

    public ClientDetails toEntity() {

        AuthClient authClient = new AuthClient();
        authClient.setClientId(clientId);
        authClient.setClientSecret(clientSecret);
        authClient.setResourceIds(StringUtils.commaDelimitedListToSet(resourceIds));
        authClient.setScope(StringUtils.commaDelimitedListToSet(scope));
        authClient.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet(authorizedGrantTypes));
        authClient.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet(registeredRedirectUri));

        List<GrantedAuthority> authorities = StringUtils.commaDelimitedListToSet(this.authorities)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        authClient.setAuthorities(authorities);

        authClient.setAccessTokenValiditySeconds(accessTokenValidity);
        authClient.setRefreshTokenValiditySeconds(refreshTokenValidity);
        authClient.setAutoApprove(autoApprove);
        authClient.setAdditionalInformation(additionalInformation);

        return authClient;
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


    public String getRegisteredRedirectUri() {

        return registeredRedirectUri;
    }


    public void setRegisteredRedirectUri(String registeredRedirectUri) {

        this.registeredRedirectUri = registeredRedirectUri;
    }


    public String getAuthorities() {

        return authorities;
    }


    public void setAuthorities(String authorities) {

        this.authorities = authorities;
    }


    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public boolean isAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
