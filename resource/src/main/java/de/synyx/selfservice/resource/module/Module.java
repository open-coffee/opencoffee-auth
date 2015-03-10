package de.synyx.selfservice.resource.module;

import de.synyx.selfservice.resource.script.Script;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
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

    private String name;

    private String uri;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "moduleId", referencedColumnName = "id")
    @RestResource(exported = false)
    private List<Script> scripts;

    public Module(){
        scripts = new ArrayList<Script>();
    }

}
