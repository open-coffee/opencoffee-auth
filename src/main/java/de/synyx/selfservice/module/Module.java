package de.synyx.selfservice.module;

import de.synyx.selfservice.event.Event;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by klem on 18.02.15.
 */

@Getter
@Setter
@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String uri;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "moduleId", referencedColumnName = "id")
    @RestResource(exported = false)
    private List<Event> events;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "moduleId", referencedColumnName = "id")
    @RestResource(exported = false)
    private List<ModuleActuator> actuators;

    public Module(){
        events = new ArrayList<Event>();
    }

}
