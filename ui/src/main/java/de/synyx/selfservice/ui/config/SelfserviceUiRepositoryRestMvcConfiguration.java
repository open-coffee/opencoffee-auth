package de.synyx.selfservice.ui.config;

import de.synyx.selfservice.ui.module.Module;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import java.net.URI;

/**
 * Created by klem on 28.04.15.
 */
@Configuration
public class SelfserviceUiRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

    private static final String MY_BASE_URI_URI = "/api";

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config);
        config.setBaseUri(URI.create(MY_BASE_URI_URI));
        config.exposeIdsFor(Module.class);
        config.setReturnBodyOnCreate(true);
    }
}
