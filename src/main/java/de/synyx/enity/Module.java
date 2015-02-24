package de.synyx.enity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by klem on 18.02.15.
 */

@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private URI uri;

    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "EventId", referencedColumnName = "Id")
    @RestResource(exported = false)
    private List<Event> events;

    public Module(){
        events = new ArrayList<Event>();
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
