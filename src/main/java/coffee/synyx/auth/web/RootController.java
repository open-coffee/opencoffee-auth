package coffee.synyx.auth.web;

import coffee.synyx.auth.config.AuthServerConfigurationProperties;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * Used to avoid confusion when user visits "/" and is redirected to an empty page after login.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Controller
@EnableConfigurationProperties(AuthServerConfigurationProperties.class)
public class RootController {

    private final AuthServerConfigurationProperties authServerConfigurationProperties;

    @Autowired
    public RootController(AuthServerConfigurationProperties authServerConfigurationProperties) {

        this.authServerConfigurationProperties = authServerConfigurationProperties;
    }

    @RequestMapping(value = "/", method = GET)
    public String redirectToRoot() {

        return "redirect:" + authServerConfigurationProperties.getDefaultRedirectUrl();
    }
}
