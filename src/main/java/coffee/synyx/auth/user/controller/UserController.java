package coffee.synyx.auth.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public Principal getUser(Principal user) {

        return user;
    }
}
