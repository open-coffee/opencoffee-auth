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
    private String name;

    @Column(unique = true)
    private URI uri;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ModuleName", referencedColumnName = "name")
    @RestResource(exported = false)
    private List<Event> events;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ModuleName", referencedColumnName = "name")
    @RestResource(exported = false)
    private List<ModuleActuator> actuators;

    public Module(){
        events = new ArrayList<Event>();
    }

}
