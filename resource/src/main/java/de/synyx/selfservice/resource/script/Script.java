package de.synyx.selfservice.resource.script;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by klem on 20.02.15.
 */

@Getter
@Setter
@Entity
public class Script extends ResourceSupport{

    @Id
    @GeneratedValue
    Long scriptId;

    private String src;

}
