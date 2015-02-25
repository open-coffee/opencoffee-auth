package de.synyx.core;

import de.synyx.Selfservice;
import de.synyx.event.Event;
import de.synyx.event.EventListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

/**
 * Created by klem on 20.02.15.
 */
@Service
public class EventListenerProcessor {

    Logger logger = Logger.getLogger(Selfservice.class);

    private static String ERROR_BASE = "Unerwarteter Fehler bei Eventbenachrichtigung von Modul \"%s\": ";
    
    public void processEventListener(EventListener eventListener){
        RestTemplate template = new RestTemplate();

        try {
            URI eventUri = new URI(eventListener.getModule().getUri().toString() + Selfservice.MODULES_EVENT_ENDPOINT);
            HttpEntity<Event> event = new HttpEntity<Event>(eventListener.getEvent());

            ResponseEntity<Event> response = template.exchange(eventUri, HttpMethod.PUT, event, Event.class);

            if(response.getStatusCode() != HttpStatus.CREATED){
                logger.error(String.format(ERROR_BASE + "Http-Statuscode: %s."
                        , eventListener.getModule().getName(), response.getStatusCode().toString()));
            }
        }catch (URISyntaxException e){
            logger.error(String.format(ERROR_BASE + "%s.", eventListener.getModule().getName(), e.getMessage()));
        }

    }
}
