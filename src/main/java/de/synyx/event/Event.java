package de.synyx.event;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URI;

/**
 * Created by klem on 20.02.15.
 */

@Getter
@Setter
@Entity
public class Event {

    @Id
    private String name;

}
