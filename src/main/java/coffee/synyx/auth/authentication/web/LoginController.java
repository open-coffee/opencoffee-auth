package coffee.synyx.auth.authentication.web;

import coffee.synyx.auth.config.AuthConfigurationProperties;

import coffee.synyx.autoconfigure.discovery.service.AppQuery;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import java.util.List;

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
    private final CoffeeNetAppService coffeeNetAppService;

    @Autowired
    public LoginController(AuthConfigurationProperties authConfigurationProperties,
        CoffeeNetAppService coffeeNetAppService) {

        this.authConfigurationProperties = authConfigurationProperties;
        this.coffeeNetAppService = coffeeNetAppService;
    }

    @RequestMapping(value = "/login", method = GET)
    public String getLoginView(Model model, Principal principal) {

        String view = "auth/login";

        if (principal == null) {
            model.addAttribute("passwordRecoveryUrl", getPasswordRecoveryUri());
        } else {
            view = "redirect:" + authConfigurationProperties.getDefaultRedirectUrl();
        }

        LOGGER.info("//> Send {} after requesting login to {}", principal != null ? principal.getName() : "anonymous",
            view);

        return view;
    }


    private String getPasswordRecoveryUri() {

        String passwordRecoveryServiceName = authConfigurationProperties.getPasswordRecoveryServiceName();

        AppQuery query = AppQuery.builder().withAppName(passwordRecoveryServiceName).build();
        List<CoffeeNetApp> profileApps = coffeeNetAppService.getApps(query).get(passwordRecoveryServiceName);

        if (profileApps != null) {
            CoffeeNetApp profileService = profileApps.get(0);

            return profileService.getUrl() + authConfigurationProperties.getPasswordRecoveryPath();
        }

        return null;
    }
}
