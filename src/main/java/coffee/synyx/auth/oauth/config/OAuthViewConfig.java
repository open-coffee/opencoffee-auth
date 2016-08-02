package coffee.synyx.auth.oauth.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Configuration
public class OAuthViewConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/oauth/confirm_access").setViewName("oauth/confirm_application_access");
    }
}
