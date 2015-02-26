package de.synyx.selfservice.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.net.URI;

/**
 * Created by klem on 20.02.15.
 */

@Getter
@Setter
@Entity
public class Event extends ResourceSupport{

    @Id
    private String name;

}
