package coffee.synyx.auth.oauth.config;

import org.slf4j.Logger;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
public class OAuthViewConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/oauth/confirm_access").setViewName("oauth/confirm_application_access");

        LOGGER.info("//> OAuthViewConfig: created view controller for /oauth/confirm_access");
    }
}
