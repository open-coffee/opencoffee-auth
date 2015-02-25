package de.synyx.controller;

import com.google.common.collect.Lists;
import de.synyx.core.EventProcessor;
import de.synyx.event.Event;
import de.synyx.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by klem on 20.02.15.
 */
@RestController
@RequestMapping("/EventProcessor")
//@EnableHypermediaSupport(type = {HypermediaType.HAL})
public class EventProcessorController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventProcessor eventProcessor;

    @RequestMapping(method = RequestMethod.POST, value = "{eventName}")
    public void processEvent(@PathVariable String eventName){
        ArrayList<Event> events = Lists.newArrayList(eventRepository.findOne(eventName));
        for(Event event : events){
            eventProcessor.processEvent(event);
        }
    }
}
