package de.synyx.selfservice.event;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by klem on 18.02.15.
 */
public interface EventRepository extends CrudRepository<Event, String> {

}
