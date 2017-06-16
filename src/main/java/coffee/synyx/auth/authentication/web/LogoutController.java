package coffee.synyx.auth.authentication.web;

import coffee.synyx.auth.config.AuthConfigurationProperties;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static org.slf4j.LoggerFactory.getLogger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Controller
@EnableConfigurationProperties(AuthConfigurationProperties.class)
public class LogoutController {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final AuthConfigurationProperties authConfigurationProperties;

    @Autowired
    public LogoutController(AuthConfigurationProperties authConfigurationProperties) {

        this.authConfigurationProperties = authConfigurationProperties;
    }

    /**
     * @param  principal  The current principal.
     * @param  referrer  The value of the "referer"-Header. This is not misspelled by accident.
     *
     * @return  The name of the view.
     *
     * @see  <a href="https://en.wikipedia.org/wiki/HTTP_referer">Wikipedia</a> for reason of mispelling.
     */
    @RequestMapping(value = "logout", method = GET)
    public String getLogoutView(Principal principal,
        @RequestHeader(value = "referer", required = false) String referrer) {

        String view = "auth/logout";

        if (principal == null) {
            if (referrer == null) {
                view = "redirect:" + authConfigurationProperties.getDefaultRedirectUrl();
            } else {
                view = "redirect:" + referrer;
            }
        }

        LOGGER.info("//> Send {} after logout to {}", principal != null ? principal.getName() : "anonymous", view);

        return view;
    }
}
