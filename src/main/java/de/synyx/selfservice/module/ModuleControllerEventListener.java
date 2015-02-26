package de.synyx.selfservice.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.synyx.selfservice.Selfservice;
import de.synyx.selfservice.event.Event;
import de.synyx.selfservice.event.EventRepository;
import de.synyx.selfservice.event.EventResource;
import de.synyx.selfservice.event.EventResources;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by klem on 26.02.15.
 */
@Component
public class ModuleControllerEventListener extends AbstractRepositoryEventListener<Module> {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    Logger logger = Logger.getLogger(Selfservice.class);

    private static String ERROR_BASE = "Unerwarteter Fehler beim Abfragen der Events von Modul \"%s\": ";

    @Override
    protected void onAfterCreate(Module module) {
        updateEventsOfModule(module);
    }

    private void updateEventsOfModule(Module module) {
        List<Event> events = getEvents(module);

        module.setEvents(events);
        moduleRepository.save(module);

    }

    private List<Event> getEvents(Module module) {
        try {
            URI eventUri = new URI(module.getUri() + Selfservice.MODULES_EVENT_ENDPOINT);
            EventResources eventResources = restTemplate().getForObject(eventUri, EventResources.class);
            return new ArrayList<Event>(eventResources.getContent());
        } catch (URISyntaxException e) {
            logger.error(String.format(ERROR_BASE + "%s.", module.getName(), e.getMessage()));
            return new ArrayList<Event>(0);
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
