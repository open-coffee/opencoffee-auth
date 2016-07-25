package coffee.synyx.auth.web;

import org.springframework.security.oauth2.provider.ClientDetails;

import org.springframework.util.StringUtils;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
public class ClientDetailsResource {

    private final ClientDetails clientDetails;

    public ClientDetailsResource(ClientDetails clientDetails) {

        this.clientDetails = clientDetails;
    }

    public String getClientId() {

        return clientDetails.getClientId();
    }


    public String getScope() {

        return StringUtils.collectionToCommaDelimitedString(clientDetails.getScope());
    }


    public String getRegisteredRedirectUri() {

        return StringUtils.collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri());
    }
}
