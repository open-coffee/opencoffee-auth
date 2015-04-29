package de.synyx.selfservice.ui.module;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by klem on 29.04.15.
 */
@Entity
public class ModuleView {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    private List<ModuleAction> actions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ModuleAction> getActions() {
        return actions;
    }

    public void setActions(List<ModuleAction> actions) {
        this.actions = actions;
    }
}
