package de.synyx.selfservice.ui.module;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Created by klem on 14.04.15.
 */

@Entity
public class SelfserviceModule {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String protocol;
    private String host;
    private int port;
    private String urlPath;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
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
