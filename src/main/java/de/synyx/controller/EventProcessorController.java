package de.synyx.controller;

import com.google.common.collect.Lists;
import de.synyx.core.EventProcessor;
import de.synyx.enity.Event;
import de.synyx.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

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

    @RequestMapping(method = RequestMethod.POST)
    public void processEvent(@RequestBody String name){
        ArrayList<Event> events = Lists.newArrayList(eventRepository.findByName(name));
        for(Event event : events){
            eventProcessor.processEvent(event);
        }
    }
}
