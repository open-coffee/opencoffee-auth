package coffee.synyx.auth.web;

import coffee.synyx.auth.config.AuthConfigurationProperties;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Controller;

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
public class LoginController {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

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

        LOGGER.info("//> Send {} after requesting login to {}", principal != null ? principal.getName() : "anonymous",
            view);

        return view;
    }
}
