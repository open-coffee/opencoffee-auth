package coffee.synyx.auth.authorization.client.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;
import static org.springframework.util.StringUtils.commaDelimitedListToSet;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
class AuthClientMapper {

    static AuthClient toEntity(AuthClientDto authClientDto) {

        AuthClient authClient = new AuthClient();
        authClient.setClientId(authClientDto.getClientId());
        authClient.setClientSecret(authClientDto.getClientSecret());
        authClient.setResourceIds(commaDelimitedListToSet(authClientDto.getResourceIds()));
        authClient.setScope(commaDelimitedListToSet(authClientDto.getScope()));
        authClient.setAuthorizedGrantTypes(commaDelimitedListToSet(authClientDto.getAuthorizedGrantTypes()));
        authClient.setRegisteredRedirectUri(commaDelimitedListToSet(authClientDto.getRegisteredRedirectUri()));

        List<GrantedAuthority> grantedAuthorities = commaDelimitedListToSet(authClientDto.getAuthorities()).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        authClient.setAuthorities(grantedAuthorities);

        authClient.setAccessTokenValiditySeconds(authClientDto.getAccessTokenValidity());
        authClient.setRefreshTokenValiditySeconds(authClientDto.getRefreshTokenValidity());
        authClient.setAutoApprove(true);
        authClient.setAdditionalInformation(authClientDto.getAdditionalInformation());

        return authClient;
    }


    static AuthClientDto toDto(ClientDetails clientDetails) {

        AuthClientDto authClientDto = new AuthClientDto();

        authClientDto.setClientId(clientDetails.getClientId());
        authClientDto.setClientSecret(clientDetails.getClientSecret());
        authClientDto.setResourceIds(collectionToCommaDelimitedString(clientDetails.getResourceIds()));
        authClientDto.setScope(collectionToCommaDelimitedString(clientDetails.getScope()));
        authClientDto.setAuthorizedGrantTypes(collectionToCommaDelimitedString(
                clientDetails.getAuthorizedGrantTypes()));

        authClientDto.setRegisteredRedirectUri(collectionToCommaDelimitedString(
                clientDetails.getRegisteredRedirectUri()));

        authClientDto.setAuthorities(collectionToCommaDelimitedString(clientDetails.getAuthorities()));
        authClientDto.setAccessTokenValidity(clientDetails.getAccessTokenValiditySeconds());
        authClientDto.setRefreshTokenValidity(clientDetails.getRefreshTokenValiditySeconds());
        authClientDto.setAdditionalInformation(clientDetails.getAdditionalInformation());

        return authClientDto;
    }
}
