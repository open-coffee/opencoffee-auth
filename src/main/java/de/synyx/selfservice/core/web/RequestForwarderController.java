package de.synyx.selfservice.core.web;

import com.google.common.collect.Lists;
import de.synyx.selfservice.event.Event;
import de.synyx.selfservice.event.EventListener;
import de.synyx.selfservice.module.Module;
import de.synyx.selfservice.module.ModuleActuator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by klem on 26.02.15.
 */
@RestController
@RequestMapping("/")
public class RequestForwarderController
{
    @RequestMapping(value = "/{moduleName}/{actuatorId}", method = RequestMethod.PUT)
    public void processEvent(@PathVariable("moduleName") Module module, @PathVariable("actuatorId") ModuleActuator actuator){
            if(module == null || actuator == null){

            }
    }
}
