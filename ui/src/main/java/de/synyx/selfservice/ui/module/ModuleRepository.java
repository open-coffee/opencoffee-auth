package de.synyx.selfservice.ui.module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by klem on 14.04.15.
 */
@RepositoryRestResource(path = "/selfserviceModules")
public interface ModuleRepository extends CrudRepository<Module, Long> {

    public Module findByName(String name);
}
