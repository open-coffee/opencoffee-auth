package coffee.synyx.auth.oauth.user.api;

import coffee.synyx.auth.AuthenticationServer;
import coffee.synyx.auth.oauth.user.service.SynyxUserDetails;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthenticationServer.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }


    @Test
    public void userIsNull() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/user"));
        resultActions.andExpect(status().isUnauthorized());
    }


    @Test
    public void userNotOAuth2() throws Exception {

        Principal principal = () -> "NOT_OAuth2Authentication";

        ResultActions resultActions = mockMvc.perform(get("/user").principal(principal));
        resultActions.andExpect(status().isUnauthorized());
    }


    @Test
    public void userOAuth2() throws Exception {

        LdapUserDetails ldapUserDetailsMock = mock(LdapUserDetails.class);
        when(ldapUserDetailsMock.getUsername()).thenReturn("klem");

        SynyxUserDetails synyxUserDetails = new SynyxUserDetails(ldapUserDetailsMock, "klem@synyx.de");
        Authentication userAuthenticationMock = mock(Authentication.class);
        when(userAuthenticationMock.isAuthenticated()).thenReturn(true);
        when(userAuthenticationMock.getPrincipal()).thenReturn(synyxUserDetails);

        OAuth2Request oAuthRequestMock = mock(OAuth2Request.class);
        when(oAuthRequestMock.isApproved()).thenReturn(true);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuthRequestMock, userAuthenticationMock);

        ResultActions resultActions = mockMvc.perform(get("/user").principal(oAuth2Authentication));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        resultActions.andExpect(jsonPath("$.name").value("klem"));
        resultActions.andExpect(jsonPath("$.id").value("klem"));
        resultActions.andExpect(jsonPath("$.principal.username").value("klem"));
        resultActions.andExpect(jsonPath("$.principal.mail").value("klem@synyx.de"));
        resultActions.andExpect(jsonPath("$.principal.password").doesNotExist());
        resultActions.andExpect(jsonPath("$.principal.authorities").exists());
        resultActions.andExpect(jsonPath("$.password").doesNotExist());
    }
}
