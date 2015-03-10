package de.synyx.selfservice.resource.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.synyx.selfservice.resource.ResourceApplication;
import de.synyx.selfservice.resource.script.Script;
import de.synyx.selfservice.resource.script.ScriptRepository;
import de.synyx.selfservice.resource.script.web.ScriptResources;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by klem on 26.02.15.
 */
@Component
public class ModuleControllerEventListener extends AbstractRepositoryEventListener<Module> {

    @Autowired
    private ScriptRepository eventRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    Logger logger = Logger.getLogger(ResourceApplication.class);

    private static String ERROR_BASE = "Unerwarteter Fehler beim Abfragen der Scripts von Modul \"%s\": ";

    @Override
    protected void onAfterCreate(Module module) {
        updateScriptsOfModule(module);
    }

    private void updateScriptsOfModule(Module module) {
        List<Script> scripts = getScripts(module);
        module.setScripts(scripts);
        moduleRepository.save(module);

    }

    private List<Script> getScripts(Module module) {
        try {
            URI scriptURI = new URI(module.getUri() + ResourceApplication.MODULES_SCRIPTS_ENDPOINT);
            ScriptResources scriptResources = restTemplate().getForObject(scriptURI, ScriptResources.class);
            ArrayList<Script> scripts = new ArrayList<Script>(scriptResources.getContent());
            for(Script script : scripts){
                script.setSrc(module.getUri()+script.getSrc());
            }
            return scripts;
        } catch (URISyntaxException e) {
            logger.error(String.format(ERROR_BASE + "%s.", module.getName(), e.getMessage()));
            return new ArrayList<Script>(0);
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jackson2HalModule());

        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        return new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
    }

}
