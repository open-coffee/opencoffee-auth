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
public class SelfserviceModule {

    //Just unused because @Getter and @Setter instead of implementing getters and setters by myself
    @Id
    @GeneratedValue
    private Long id; //NOSONAR

    private String name; //NOSONAR
    private String protocol; //NOSONAR
    private String host; //NOSONAR
    private int port; //NOSONAR
    private String urlPath; //NOSONAR


}
