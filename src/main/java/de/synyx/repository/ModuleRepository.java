package de.synyx.repository;

import de.synyx.enity.Module;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by klem on 18.02.15.
 */
public interface ModuleRepository extends CrudRepository<Module, Long> {

    public Module findByName(String name);

}
