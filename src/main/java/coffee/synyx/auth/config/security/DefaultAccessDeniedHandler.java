package coffee.synyx.auth.config.security;

import coffee.synyx.auth.config.AuthServerConfigurationProperties;

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


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@EnableConfigurationProperties(AuthServerConfigurationProperties.class)
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    private final HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();

    private final AuthServerConfigurationProperties authServerConfigurationProperties;

    public DefaultAccessDeniedHandler(AuthServerConfigurationProperties authServerConfigurationProperties) {

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

            response.sendRedirect(redirectUrl);
        }
    }
}
