package coffee.synyx.auth.authentication.web;

import org.slf4j.Logger;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

import static org.slf4j.LoggerFactory.getLogger;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Controller
public class AccessDeniedController {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private static final String ACCESS_DENIED = "access_denied";

    @RequestMapping(value = "/forbidden", method = GET)
    @ResponseStatus(FORBIDDEN)
    public String getForbiddenView(Principal principal) {

        LOGGER.info("//> Send {} after requesting a forbidden page to {}",
            principal != null ? principal.getName() : "anonymous", ACCESS_DENIED);

        return ACCESS_DENIED;
    }
}
