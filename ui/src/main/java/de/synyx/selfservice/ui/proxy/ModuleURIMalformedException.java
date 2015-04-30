package de.synyx.selfservice.ui.proxy;

import de.synyx.selfservice.ui.module.Module;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by klem on 30.04.15.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ModuleURIMalformedException extends RuntimeException{
    public ModuleURIMalformedException(){
        super();
    }

    public ModuleURIMalformedException(String message){
        super(message);
    }
}
