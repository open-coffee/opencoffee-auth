package coffee.synyx.auth.web;

import coffee.synyx.auth.config.AuthConfigurationProperties;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

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
    private final DiscoveryClient discoveryClient;

    @Autowired
    public LoginController(AuthConfigurationProperties authConfigurationProperties, DiscoveryClient discoveryClient) {

        this.authConfigurationProperties = authConfigurationProperties;
        this.discoveryClient = discoveryClient;
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
        List<ServiceInstance> profileServices = discoveryClient.getInstances(passwordRecoveryServiceName);

        if (!profileServices.isEmpty()) {
            ServiceInstance profileService = profileServices.get(0);

            if (profileService instanceof EurekaServiceInstance) {
                EurekaServiceInstance eurekaServiceProfile = (EurekaServiceInstance) profileService;

                return eurekaServiceProfile.getInstanceInfo().getHomePageUrl()
                    + authConfigurationProperties.getPasswordRecoveryPath();
            }
        }

        return null;
    }
}
