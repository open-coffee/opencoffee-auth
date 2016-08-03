package coffee.synyx.auth.web;

import coffee.synyx.auth.config.AuthServerConfigurationProperties;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Controller
@EnableConfigurationProperties(AuthServerConfigurationProperties.class)
public class LogoutController {

    private final AuthServerConfigurationProperties authServerConfigurationProperties;

    @Autowired
    public LogoutController(AuthServerConfigurationProperties authServerConfigurationProperties) {

        this.authServerConfigurationProperties = authServerConfigurationProperties;
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

        String view = "logout";

        if (principal == null) {
            if (referrer == null) {
                view = "redirect:" + authServerConfigurationProperties.getDefaultRedirectUrl();
            } else {
                view = "redirect:" + referrer;
            }
        }

        return view;
    }
}
