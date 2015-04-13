package de.synyx.selfservice.resource.module;

import de.synyx.selfservice.resource.script.Script;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by klem on 18.02.15.
 */

@Getter
@Setter
@Entity
public class Module extends ResourceSupport{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long moduleId;

    private String name;

    private String uri;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "moduleId", referencedColumnName = "moduleId")
    @RestResource(exported = false)
    private List<Script> scripts;

    public Module(){
        scripts = new ArrayList<Script>();
    }

}
