package de.synyx.selfservice.ui.proxy;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by klem on 30.04.15.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProxyRequestBodyException extends RuntimeException{
    public ProxyRequestBodyException(){
        super();
    }

    public ProxyRequestBodyException(String message){
        super(message);
    }
}
