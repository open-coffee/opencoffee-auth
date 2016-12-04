package coffee.synyx.auth.web;

import coffee.synyx.auth.config.AuthConfigurationProperties;

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
@EnableConfigurationProperties(AuthConfigurationProperties.class)
public class RootController {

    private final AuthConfigurationProperties authConfigurationProperties;

    @Autowired
    public RootController(AuthConfigurationProperties authConfigurationProperties) {

        this.authConfigurationProperties = authConfigurationProperties;
    }

    @RequestMapping(value = "/", method = GET)
    public String redirectToRoot() {

        return "redirect:" + authConfigurationProperties.getDefaultRedirectUrl();
    }
}
