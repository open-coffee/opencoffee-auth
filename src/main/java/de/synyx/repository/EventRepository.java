package de.synyx.repository;

import de.synyx.enity.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by klem on 18.02.15.
 */
public interface EventRepository extends CrudRepository<Event, Long> {

    public Iterable<Event> findByName(String name);

}
