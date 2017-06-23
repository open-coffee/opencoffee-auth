package coffee.synyx.auth.authorization.client.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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


    static AuthClientDto toDto(AuthClient authClient) {

        AuthClientDto authClientDto = new AuthClientDto();

        authClientDto.setClientId(authClient.getClientId());
        authClientDto.setClientSecret(authClient.getClientSecret());
        authClientDto.setResourceIds(collectionToCommaDelimitedString(authClient.getResourceIds()));
        authClientDto.setScope(collectionToCommaDelimitedString(authClient.getScope()));
        authClientDto.setAuthorizedGrantTypes(collectionToCommaDelimitedString(authClient.getAuthorizedGrantTypes()));

        authClientDto.setRegisteredRedirectUri(collectionToCommaDelimitedString(
                authClient.getRegisteredRedirectUri()));

        authClientDto.setAuthorities(collectionToCommaDelimitedString(authClient.getAuthorities()));
        authClientDto.setAccessTokenValidity(authClient.getAccessTokenValiditySeconds());
        authClientDto.setRefreshTokenValidity(authClient.getRefreshTokenValiditySeconds());
        authClientDto.setAdditionalInformation(authClient.getAdditionalInformation());

        return authClientDto;
    }
}
