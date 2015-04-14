package de.synyx.selfservice.ui.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by klem on 14.04.15.
 */

@Entity
@Getter
@Setter
public class Module {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String protocol;
    private String host;
    private int port;
    private String urlPath;


}
