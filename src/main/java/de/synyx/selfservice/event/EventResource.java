package de.synyx.selfservice.event;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * Created by klem on 26.02.15.
 */
public class EventResource extends Resource<Event> {

    public EventResource(){
        this(new Event(), new Link[0]);
    }
    public EventResource(Event content, Link... links) {
        super(content, links);
    }

    public EventResource(Event content, Iterable<Link> links) {
        super(content, links);
    }
}
