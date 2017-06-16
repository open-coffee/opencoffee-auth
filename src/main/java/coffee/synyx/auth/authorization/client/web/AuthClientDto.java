package coffee.synyx.auth.authorization.client.web;

import org.hibernate.validator.constraints.Length;

import java.util.HashMap;
import java.util.Map;


/**
 * Dto of the {@link AuthClient}.
 *
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class AuthClientDto {

    @Length(min = 1, max = 200)
    private String clientId;

    @Length(min = 8, max = 256)
    private String clientSecret;

    @Length(min = 1, max = 256)
    private String registeredRedirectUri;

    private String resourceIds;
    private String scope;
    private String authorizedGrantTypes;
    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private Map<String, Object> additionalInformation = new HashMap<>();

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


    public String getRegisteredRedirectUri() {

        return registeredRedirectUri;
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


    public Map<String, Object> getAdditionalInformation() {

        return additionalInformation;
    }


    public void setAdditionalInformation(Map<String, Object> additionalInformation) {

        this.additionalInformation = additionalInformation;
    }


    /**
     * Needs to be public for deserialization purposes.
     *
     * @param  registeredRedirectUri  This is a comma separated list of uris where the client can be accessed with a
     *                                maximum length of 256 characters. I.e.
     *                                "https://first-app.coffeenet,http://second-app.coffeenet"
     */
    public void setRegisteredRedirectUri(String registeredRedirectUri) {

        this.registeredRedirectUri = beautifyRedirectUriString(registeredRedirectUri);
    }


    private static String beautifyRedirectUriString(String registeredRedirectUri) {

        String beautifiedRegisteredRedirectUri = registeredRedirectUri.replaceAll("\\s+", "");

        int length = beautifiedRegisteredRedirectUri.length();
        int startIndex = 0;
        int endIndex = length - 1;

        while (startIndex < length && beautifiedRegisteredRedirectUri.charAt(startIndex) == ',') {
            startIndex++;
        }

        while (endIndex >= 0 && beautifiedRegisteredRedirectUri.charAt(endIndex) == ',') {
            endIndex--;
        }

        return beautifiedRegisteredRedirectUri.substring(startIndex, endIndex + 1);
    }
}
