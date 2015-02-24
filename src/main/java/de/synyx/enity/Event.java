package de.synyx.enity;

import javax.persistence.*;
import java.net.URI;

/**
 * Created by klem on 20.02.15.
 */

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private URI triggerURI;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getTriggerURI() {
        return triggerURI;
    }

    public void setTriggerURI(URI triggerURI) {
        this.triggerURI = triggerURI;
    }
}
