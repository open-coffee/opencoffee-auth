package coffee.synyx.auth.authentication.web;

import coffee.synyx.auth.AuthConfigurationProperties;

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
 * Controller to handle the {@link coffee.synyx.autoconfigure.security.service.AbstractCoffeeNetUser} login calls.
 *
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider
 */
@Controller
@EnableConfigurationProperties(AuthConfigurationProperties.class)
public class LoginController {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private final AuthConfigurationProperties authConfigurationProperties;
    private CoffeeNetAppService coffeeNetAppService;

    @Autowired
    public LoginController(AuthConfigurationProperties authConfigurationProperties) {

        this.authConfigurationProperties = authConfigurationProperties;
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

        if (coffeeNetAppService != null) {
            LOGGER.info("//> Trying to find the password recovery service (search '{}')", passwordRecoveryServiceName);

            AppQuery query = AppQuery.builder().withAppName(passwordRecoveryServiceName).build();
            List<CoffeeNetApp> profileApps = coffeeNetAppService.getApps(query).get(passwordRecoveryServiceName);

            if (profileApps != null) {
                CoffeeNetApp profileService = profileApps.get(0);

                LOGGER.info("//> Password recovery service '{}' found", passwordRecoveryServiceName);

                return profileService.getUrl() + authConfigurationProperties.getPasswordRecoveryPath();
            }
        }

        LOGGER.info("//> No password recovery service found", passwordRecoveryServiceName);

        return null;
    }


    /**
     * Optional dependency if the service discovery is activated. For this optional dependency we use setter injection
     *
     * @param  coffeeNetAppService  to find the password recovery application
     */
    @Autowired(required = false)
    public void setCoffeeNetAppService(CoffeeNetAppService coffeeNetAppService) {

        this.coffeeNetAppService = coffeeNetAppService;
    }
}
