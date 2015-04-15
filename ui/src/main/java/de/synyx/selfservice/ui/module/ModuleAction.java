package de.synyx.selfservice.ui.module;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by klem on 15.04.15.
 */

@Entity
public class ModuleAction {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private  String endpoint;

    @NotNull
    private ModuleActionAccept accept;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "action_id", referencedColumnName = "id")
    private Set<ModuleActionParameter> params;

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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public ModuleActionAccept getAccept() {
        return accept;
    }

    public void setAccept(ModuleActionAccept accept) {
        this.accept = accept;
    }

    public Set<ModuleActionParameter> getParams() {
        return params;
    }

    public void setParams(Set<ModuleActionParameter> params) {
        this.params = params;
    }

    public enum ModuleActionAccept {
        JSON, XML, URLENC
    }

}
