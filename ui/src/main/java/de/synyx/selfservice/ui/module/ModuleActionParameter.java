package de.synyx.selfservice.ui.module;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

/**
 * Created by klem on 15.04.15.
 */

@Entity
public class ModuleActionParameter {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private ModuleActionParameterType type;

    @ElementCollection
    private Map<String,String> limits;

    @ElementCollection
    private Set<String> options;

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

    public ModuleActionParameterType getType() {
        return type;
    }

    public void setType(ModuleActionParameterType type) {
        this.type = type;
    }

    public Map<String, String> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, String> limits) {
        this.limits = limits;
    }

    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }

    public enum ModuleActionParameterType{
        TEXT, NUMBER, EMAIL, DATE, PASSWORD
    }
}
