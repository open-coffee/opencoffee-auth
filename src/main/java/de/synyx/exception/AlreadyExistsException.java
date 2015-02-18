package de.synyx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by klem on 18.02.15.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Diese Ressource existiert bereits")
public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message){
        super(message);
    }
}
