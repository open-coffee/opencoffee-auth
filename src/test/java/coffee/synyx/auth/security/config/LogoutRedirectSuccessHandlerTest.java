package coffee.synyx.auth.security.config;

import coffee.synyx.auth.user.SynyxAuthentication;
import coffee.synyx.auth.user.userdetails.SynyxUserDetails;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Unit test of {@link LogoutRedirectSuccessHandler}.
 *
 * @author  Tobias Schneider
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LogoutRedirectSuccessHandler.class)
public class LogoutRedirectSuccessHandlerTest {

    @Autowired
    private LogoutRedirectSuccessHandler sut;

    private HttpServletRequest requestMock = mock(HttpServletRequest.class);
    private HttpServletResponse responseMock = mock(HttpServletResponse.class);

    @Test
    public void onLogoutSuccess() throws IOException {

        String notTheDefaultRedirectUrl = "notTheDefaultRedirectUrl";
        when(requestMock.getParameter("redirect")).thenReturn(notTheDefaultRedirectUrl);

        sut.onLogoutSuccess(requestMock, responseMock, getOAuth2Authentication());

        verify(requestMock).getParameter("redirect");
        verify(responseMock).sendRedirect(notTheDefaultRedirectUrl);
    }


    @Test
    public void onLogoutSuccessWithDefaultRedirectURI() throws IOException {

        sut.onLogoutSuccess(requestMock, responseMock, getOAuth2Authentication());

        verify(requestMock).getParameter("redirect");
        verify(responseMock).sendRedirect("${synyx.security.default.redirectUri}");
    }


    private OAuth2Authentication getOAuth2Authentication() {

        SynyxUserDetails synyxUserDetails = new SynyxUserDetails(mock(LdapUserDetails.class));

        Authentication userAuthenticationMock = mock(Authentication.class);
        when(userAuthenticationMock.isAuthenticated()).thenReturn(true);
        when(userAuthenticationMock.getPrincipal()).thenReturn(synyxUserDetails);

        OAuth2Request oAuthRequestMock = mock(OAuth2Request.class);
        when(oAuthRequestMock.isApproved()).thenReturn(true);

        return new SynyxAuthentication(new OAuth2Authentication(oAuthRequestMock, userAuthenticationMock));
    }
}
