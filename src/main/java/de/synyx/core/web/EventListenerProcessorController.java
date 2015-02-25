package de.synyx.core.web;

import com.google.common.collect.Lists;
import de.synyx.Selfservice;
import de.synyx.core.EventListenerProcessor;
import de.synyx.event.Event;
import de.synyx.event.EventListener;
import de.synyx.event.EventListenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by klem on 20.02.15.
 */
@RestController
@RequestMapping(Selfservice.EVENT_LISTENER_PROCESSOR_CONTROLLER_ENDPOINT)
public class EventListenerProcessorController {

    @Autowired
    EventListenerRepository eventListenerRepository;

    @Autowired
    EventListenerProcessor eventListenerProcessor;

    @RequestMapping(value = "/{eventName}", method = RequestMethod.PUT)
    public void processEvent(@PathVariable("eventName") Event event){

        if(event == null){
            throw new EventDoesNotExistException();
        }

        ArrayList<EventListener> eventListeners = Lists.newArrayList(eventListenerRepository.findByEvent(event));

        for(EventListener eventListener : eventListeners){
            eventListenerProcessor.processEventListener(eventListener);
        }

    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "No such event.")
    public class EventDoesNotExistException extends RuntimeException{
    }
}
