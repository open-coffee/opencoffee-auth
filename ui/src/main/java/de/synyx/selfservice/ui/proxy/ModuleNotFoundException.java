package de.synyx.selfservice.ui.proxy;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by klem on 30.04.15.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ModuleNotFoundException extends RuntimeException{
    public ModuleNotFoundException(){
        super();
    }

    public ModuleNotFoundException(String moduleName){
        super("No such module: " + moduleName);
    }
}
