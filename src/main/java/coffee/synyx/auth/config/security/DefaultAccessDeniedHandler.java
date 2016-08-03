package coffee.synyx.auth.config.security;

import coffee.synyx.auth.config.AuthServerConfigurationProperties;

import org.slf4j.Logger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@EnableConfigurationProperties(AuthServerConfigurationProperties.class)
class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());
    private final HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
    private final AuthServerConfigurationProperties authServerConfigurationProperties;

    DefaultAccessDeniedHandler(AuthServerConfigurationProperties authServerConfigurationProperties) {

        this.authServerConfigurationProperties = authServerConfigurationProperties;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {

        if (accessDeniedException instanceof CsrfException) {
            String redirectUrl = authServerConfigurationProperties.getDefaultRedirectUrl();

            SavedRequest savedRequest = httpSessionRequestCache.getRequest(request, response);

            if (savedRequest != null) {
                redirectUrl = savedRequest.getRedirectUrl();
            }

            LOGGER.info("Handling CsrfException with redirect to {}.", redirectUrl);
            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("/forbidden");
        }
    }
}
