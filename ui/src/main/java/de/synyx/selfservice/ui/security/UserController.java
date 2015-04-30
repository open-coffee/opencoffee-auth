package de.synyx.selfservice.ui.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by klem on 30.04.15.
 */
@RestController
public class UserController {
    @RequestMapping("/api/user")
    public Principal getUser(Principal user) {
        return user;
    }
}
