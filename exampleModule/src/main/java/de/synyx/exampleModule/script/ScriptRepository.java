package de.synyx.exampleModule.script;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yannic on 27.02.2015.
 */
@Repository
public interface ScriptRepository extends CrudRepository<Script, Long>{

}
