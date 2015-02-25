package de.synyx.event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by klem on 18.02.15.
 */
@Repository
public interface EventListenerRepository extends CrudRepository<EventListener, Long> {

    public Iterable<EventListener> findByEvent(Event event);

}
