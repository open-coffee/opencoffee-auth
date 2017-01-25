package coffee.synyx.auth.config.integration.security;

import coffee.synyx.auth.config.AuthConfigurationProperties;
import coffee.synyx.auth.oauth.user.service.SynyxUserDetails;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

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
public class LogoutRedirectSuccessHandlerTest {

    private LogoutRedirectSuccessHandler sut;

    private HttpServletRequest requestMock = mock(HttpServletRequest.class);
    private HttpServletResponse responseMock = mock(HttpServletResponse.class);

    @Before
    public void setUp() throws Exception {

        sut = new LogoutRedirectSuccessHandler(new AuthConfigurationProperties());
    }


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
        verify(responseMock).sendRedirect("http://localhost:8080");
    }


    private OAuth2Authentication getOAuth2Authentication() {

        SynyxUserDetails synyxUserDetails = new SynyxUserDetails(mock(LdapUserDetails.class), "test@synyx.de");

        Authentication userAuthenticationMock = mock(Authentication.class);
        when(userAuthenticationMock.isAuthenticated()).thenReturn(true);
        when(userAuthenticationMock.getPrincipal()).thenReturn(synyxUserDetails);

        OAuth2Request oAuthRequestMock = mock(OAuth2Request.class);
        when(oAuthRequestMock.isApproved()).thenReturn(true);

        return new OAuth2Authentication(oAuthRequestMock, userAuthenticationMock);
    }
}
