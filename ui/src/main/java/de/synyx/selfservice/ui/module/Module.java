package de.synyx.selfservice.ui.module;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Created by klem on 14.04.15.
 */

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"protocol", "host", "port", "urlPath"}))
public class Module {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;
    private String protocol;
    private String host;
    private int port;
    private String urlPath;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="view_id")
    private ModuleView moduleView;


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

    public ModuleView getModuleView() {
        return moduleView;
    }

    public void setModuleView(ModuleView moduleView) {
        this.moduleView = moduleView;
    }
}
