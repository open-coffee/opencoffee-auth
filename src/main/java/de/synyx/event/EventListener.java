package de.synyx.event;

import de.synyx.module.Module;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by klem on 25.02.15.
 */
@Getter
@Setter
@Entity
public class EventListener {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Event event;

    @OneToOne
    private Module module;

}
