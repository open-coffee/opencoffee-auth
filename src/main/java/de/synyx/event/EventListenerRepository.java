package de.synyx.event;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by klem on 18.02.15.
 */
public interface EventListenerRepository extends CrudRepository<EventListener, Long> {

    public List<EventListener> findByEventName(String eventName);

}
