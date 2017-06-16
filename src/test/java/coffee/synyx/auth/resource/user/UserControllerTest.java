package coffee.synyx.auth.resource.user;

import coffee.synyx.auth.authentication.config.CoffeeNetUserDetails;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.MediaType;

import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;


/**
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class UserControllerTest {

    private UserController sut;

    @Before
    public void setupMockMvc() {

        sut = new UserController();
    }


    @Test
    public void userIsNull() throws Exception {

        ResultActions resultActions = perform(get("/user"));
        resultActions.andExpect(status().isUnauthorized());
    }


    @Test
    public void userOAuth2() throws Exception {

        LdapUserDetails ldapUserDetailsMock = mock(LdapUserDetails.class);
        when(ldapUserDetailsMock.getUsername()).thenReturn("user");

        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetails(ldapUserDetailsMock, "user@coffeenet");
        Authentication userAuthenticationMock = mock(Authentication.class);
        when(userAuthenticationMock.isAuthenticated()).thenReturn(true);
        when(userAuthenticationMock.getPrincipal()).thenReturn(coffeeNetUserDetails);

        OAuth2Request oAuthRequestMock = mock(OAuth2Request.class);
        when(oAuthRequestMock.isApproved()).thenReturn(true);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuthRequestMock, userAuthenticationMock);

        ResultActions resultActions = perform(get("/user").principal(oAuth2Authentication));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        resultActions.andExpect(jsonPath("$.name").value("user"));
        resultActions.andExpect(jsonPath("$.id").value("user"));
        resultActions.andExpect(jsonPath("$.email").value("user@coffeenet"));
        resultActions.andExpect(jsonPath("$.principal.username").value("user"));
        resultActions.andExpect(jsonPath("$.principal.mail").value("user@coffeenet"));
        resultActions.andExpect(jsonPath("$.principal.password").doesNotExist());
        resultActions.andExpect(jsonPath("$.principal.authorities").exists());
        resultActions.andExpect(jsonPath("$.password").doesNotExist());
    }


    @Test
    public void userOAuth2AsClientOnlyClient() throws Exception {

        OAuth2Request oAuth2Request = new OAuth2Request(emptyMap(), "clientId", emptyList(), true, emptySet(),
                emptySet(), "", emptySet(), emptyMap());

        Authentication clientOnly = null;
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, clientOnly);

        ResultActions resultActions = perform(get("/user").principal(oAuth2Authentication));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        resultActions.andExpect(jsonPath("$.name").value("clientId"));
        resultActions.andExpect(jsonPath("$.id").value("clientId"));
        resultActions.andExpect(jsonPath("$.email").doesNotExist());
        resultActions.andExpect(jsonPath("$.principal").value("clientId"));
        resultActions.andExpect(jsonPath("$.password").doesNotExist());
    }


    private <T> ResultActions perform(RequestBuilder builder) throws Exception {

        return standaloneSetup(sut).build().perform(builder);
    }
}
