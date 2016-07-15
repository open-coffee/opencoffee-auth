package coffee.synyx.auth.config.security;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Logout Success Handler which is responsible for redirecting to the previous used application. It parses the Request
 * param redirect and redirects to it.
 *
 * @author  David Schilling - schilling@synyx.de
 */
@Component
public class LogoutRedirectSuccessHandler implements LogoutSuccessHandler {

    @Value("${synyx.security.default.redirectUri}")
    private String defaultRedirectUri;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException {

        String redirect = request.getParameter("redirect");

        if (redirect == null) {
            redirect = defaultRedirectUri;
        }

        response.sendRedirect(redirect);
    }
}
