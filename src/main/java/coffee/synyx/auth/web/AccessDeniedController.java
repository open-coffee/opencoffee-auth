package coffee.synyx.auth.web;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Controller
public class AccessDeniedController {

    private static final String ACCESS_DENIED = "access_denied";

    @RequestMapping(value = "/forbidden", method = GET)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String getForbiddenView() {

        return ACCESS_DENIED;
    }
}
