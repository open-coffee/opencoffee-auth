package coffee.synyx.auth.web;

import coffee.synyx.auth.config.AuthServerConfigurationProperties;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Controller;

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

    @RequestMapping(value = "logout", method = GET)
    public String getLogoutView(Principal principal) {

        String view = "oauth/logout";

        if (principal == null) {
            view = "redirect:" + authServerConfigurationProperties.getDefaultRedirectUrl();
        }

        return view;
    }
}
