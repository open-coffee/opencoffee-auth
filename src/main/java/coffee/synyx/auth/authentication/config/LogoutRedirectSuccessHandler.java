package coffee.synyx.auth.authentication.config;

import coffee.synyx.auth.AuthConfigurationProperties;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Logout Success Handler which is responsible for redirecting to the previous used application. It parses the Request
 * param redirect and redirects to it.
 *
 * @author  David Schilling - schilling@synyx.de
 */
@Component
@EnableConfigurationProperties(AuthConfigurationProperties.class)
public class LogoutRedirectSuccessHandler implements LogoutSuccessHandler {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private String defaultRedirectUri;

    @Autowired
    public LogoutRedirectSuccessHandler(AuthConfigurationProperties authConfigurationProperties) {

        defaultRedirectUri = authConfigurationProperties.getDefaultRedirectUrl();

        LOGGER.info("//> LogoutRedirectSuccessHandler created");
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        String redirect = request.getParameter("redirect");

        if (redirect == null) {
            redirect = defaultRedirectUri;
        }

        LOGGER.info("//> Redirect {} after logout to {}",
            request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous",
            defaultRedirectUri);

        response.sendRedirect(redirect);
    }
}
