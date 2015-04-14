package de.synyx.selfservice.ui.module;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by klem on 14.04.15.
 */
public interface SelfserviceModuleRepository extends CrudRepository<SelfserviceModule, Long> {

    public SelfserviceModule findByName(String name);
}
