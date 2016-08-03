package coffee.synyx.auth.config.security;

import coffee.synyx.auth.config.AuthServerConfigurationProperties;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultAccessDeniedHandlerTest {

    private DefaultAccessDeniedHandler sut;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Before
    public void setup() {

        AuthServerConfigurationProperties authServerConfigurationProperties = new AuthServerConfigurationProperties();
        authServerConfigurationProperties.setDefaultRedirectUrl("https://synyx.coffee");
        sut = new DefaultAccessDeniedHandler(authServerConfigurationProperties);
    }


    @Test
    public void doesNotHandleAccessDeniedException() throws IOException, ServletException {

        sut.handle(request, response, new AccessDeniedException("Test"));

        verify(response).sendRedirect("/forbidden");
    }


    @Test
    public void handlesMissingCsrfTokenException() throws IOException, ServletException {

        sut.handle(request, response, new MissingCsrfTokenException("Test"));

        verify(response).sendRedirect("https://synyx.coffee");
    }


    @Test
    public void handlesInvalidCsrfTokenException() throws IOException, ServletException {

        sut.handle(request, response,
            new InvalidCsrfTokenException(new DefaultCsrfToken("test", "test", "test"), "Test"));

        verify(response).sendRedirect("https://synyx.coffee");
    }
}
