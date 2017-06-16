package coffee.synyx.auth.authentication.web;

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
 * Used to avoid confusion when user visits "/" and is redirected to an empty page after login.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Controller
@EnableConfigurationProperties(AuthConfigurationProperties.class)
public class RootController {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final AuthConfigurationProperties authConfigurationProperties;

    @Autowired
    public RootController(AuthConfigurationProperties authConfigurationProperties) {

        this.authConfigurationProperties = authConfigurationProperties;
    }

    @RequestMapping(value = "/", method = GET)
    public String redirectToRoot(Principal principal) {

        String defaultRedirectUrl = authConfigurationProperties.getDefaultRedirectUrl();

        LOGGER.info("//> Redirect {} after requesting / to {}", principal != null ? principal.getName() : "anonymous",
            defaultRedirectUrl);

        return "redirect:" + defaultRedirectUrl;
    }
}
