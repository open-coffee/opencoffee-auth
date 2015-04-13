package de.synyx.exampleModule.script;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Yannic on 27.02.2015.
 */
@Getter
@Setter
@Entity
public class Script {

    @Id
    @GeneratedValue
    private Long id;

    private String src;

}
