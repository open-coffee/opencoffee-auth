package coffee.synyx.auth.oauth.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Controller
@RequestMapping("/authclient")
public class AuthClientController {

    private AuthClientService authClientService;

    @Autowired
    public AuthClientController(AuthClientService authClientService) {

        this.authClientService = authClientService;
    }

    @RequestMapping(value = "/{authClientId}", method = GET)
    public String getById(@PathVariable("authClientId") String authClientId, Model model) {

        AuthClient authClient = authClientService.get(authClientId);
        model.addAttribute("authClient", authClient);

        return "authClient";
    }
}
