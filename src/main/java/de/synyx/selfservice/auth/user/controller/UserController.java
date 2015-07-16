package de.synyx.selfservice.auth.user.controller;

import de.synyx.selfservice.auth.user.userdetails.SynyxUserDetails;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * Created by klem on 13.04.15.
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public SynyxUserDetails getUser(Principal user) {

        return (SynyxUserDetails) ((OAuth2Authentication) user).getUserAuthentication().getPrincipal();
    }
}
