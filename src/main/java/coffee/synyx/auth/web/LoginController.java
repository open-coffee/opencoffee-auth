package coffee.synyx.auth.web;

import coffee.synyx.auth.config.AuthConfigurationProperties;

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
@EnableConfigurationProperties(AuthConfigurationProperties.class)
public class LoginController {

    private final AuthConfigurationProperties authConfigurationProperties;

    @Autowired
    public LoginController(AuthConfigurationProperties authConfigurationProperties) {

        this.authConfigurationProperties = authConfigurationProperties;
    }

    @RequestMapping(value = "/login", method = GET)
    public String getLoginView(Principal principal) {

        String view = "auth/login";

        if (principal != null) {
            view = "redirect:" + authConfigurationProperties.getDefaultRedirectUrl();
        }

        return view;
    }
}
