package coffee.synyx.auth.oauth.client.web;

import org.hibernate.validator.constraints.Length;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;


/**
 * Dto of the {@link ClientDetails}.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
public class ClientDetailsResource {

    private static final boolean AUTO_APPROVE = true;

    @Length(min = 1, max = 200)
    private String clientId;

    @Length(min = 8, max = 256)
    private String clientSecret;

    @Length(min = 1, max = 256)
    private String registeredRedirectUri;

    private final String resourceIds;
    private final String scope;
    private final String authorizedGrantTypes;
    private final String authorities;
    private final Integer accessTokenValidity;
    private final Integer refreshTokenValidity;
    private final Map<String, Object> additionalInformation;

    ClientDetailsResource() {

        this.scope = "openid";
        this.authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
        this.authorities = "";
        this.resourceIds = null;
        this.accessTokenValidity = null;
        this.refreshTokenValidity = null;
        this.additionalInformation = new HashMap<>();
    }


    ClientDetailsResource(ClientDetails clientDetails) {

        this.clientId = clientDetails.getClientId();
        this.clientSecret = clientDetails.getClientSecret();
        this.resourceIds = collectionToCommaDelimitedString(clientDetails.getResourceIds());
        this.scope = collectionToCommaDelimitedString(clientDetails.getScope());
        this.authorizedGrantTypes = collectionToCommaDelimitedString(clientDetails.getAuthorizedGrantTypes());

        setRegisteredRedirectUri(collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri()));

        this.authorities = collectionToCommaDelimitedString(clientDetails.getAuthorities());
        this.accessTokenValidity = clientDetails.getAccessTokenValiditySeconds();
        this.refreshTokenValidity = clientDetails.getRefreshTokenValiditySeconds();
        this.additionalInformation = clientDetails.getAdditionalInformation();
    }

    ClientDetails toEntity() {

        AuthClient authClient = new AuthClient();
        authClient.setClientId(clientId);
        authClient.setClientSecret(clientSecret);
        authClient.setResourceIds(StringUtils.commaDelimitedListToSet(resourceIds));
        authClient.setScope(StringUtils.commaDelimitedListToSet(scope));
        authClient.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet(authorizedGrantTypes));
        authClient.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet(registeredRedirectUri));

        List<GrantedAuthority> grantedAuthorities = StringUtils.commaDelimitedListToSet(this.authorities)
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        authClient.setAuthorities(grantedAuthorities);

        authClient.setAccessTokenValiditySeconds(accessTokenValidity);
        authClient.setRefreshTokenValiditySeconds(refreshTokenValidity);
        authClient.setAutoApprove(AUTO_APPROVE);
        authClient.setAdditionalInformation(additionalInformation);

        return authClient;
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


    public String getClientId() {

        return clientId;
    }


    /**
     * Needs to be public for deserialization purposes.
     *
     * @param  clientId  This could be any UTF-8 String that identifies the client with a maximum length of 200
     *                   characters.
     */
    public void setClientId(String clientId) {

        this.clientId = clientId;
    }


    public String getClientSecret() {

        return clientSecret;
    }


    /**
     * Needs to be public for deserialization purposes.
     *
     * @param  clientSecret  This could be any UTF-8 String. This is the secret for this client. The secret has a
     *                       maximum length of 256 characters.
     */
    public void setClientSecret(String clientSecret) {

        this.clientSecret = clientSecret;
    }


    public String getRegisteredRedirectUri() {

        return registeredRedirectUri;
    }


    /**
     * Needs to be public for deserialization purposes.
     *
     * @param  registeredRedirectUri  This is a comma separated list of uris where the client can be accessed with a
     *                                maximum length of 256 characters. I.e.
     *                                "https://myApp.synyx.coffee,http://myApp.synyx.coffee"
     */
    public void setRegisteredRedirectUri(String registeredRedirectUri) {

        this.registeredRedirectUri = beautifyRedirectUriString(registeredRedirectUri);
    }
}
