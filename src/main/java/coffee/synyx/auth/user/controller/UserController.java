package coffee.synyx.auth.user.controller;

import coffee.synyx.auth.user.SynyxAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public SynyxAuthentication getUser(Principal user) {

        return new SynyxAuthentication(user.getName(), user.getName());
    }
}
