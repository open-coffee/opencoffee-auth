package coffee.synyx.auth.oauth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Yannic Klem - klem@synyx.de
 */
@Controller
@RequestMapping("/clients")
public class AuthClientController {

    private JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    public AuthClientController(JdbcClientDetailsService jdbcClientDetailsService) {

        this.jdbcClientDetailsService = jdbcClientDetailsService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.addValidators(new ClientDetailsResourceValidator());
    }

    @RequestMapping(method = GET)
    public String getAllClientsView(Model model) {

        List<ClientDetailsResource> clientDetails = jdbcClientDetailsService.listClientDetails()
                .stream()
                .map(ClientDetailsResource::new)
                .collect(Collectors.toList());

        model.addAttribute("clients", clientDetails);

        return "clients/all";
    }

    @RequestMapping(value = "/new", method = GET)
    public String getNewClientView(Model model) {

        model.addAttribute("client", new ClientDetailsResource());

        return "clients/new";
    }

    @RequestMapping(method = POST)
    public String createNewClient(@Valid @ModelAttribute(value = "client") ClientDetailsResource clientDetailsResource,
                                  BindingResult binding){

        if(binding.hasErrors()) {

            return "/clients/new";
        }

        clientDetailsResource.setAutoApprove(true);
        clientDetailsResource.setAuthorizedGrantTypes("authorization_code,password,refresh_token,client_credentials");
        clientDetailsResource.setScope("openid");

        jdbcClientDetailsService.addClientDetails(clientDetailsResource.toEntity());

        return "redirect:/clients";
    }


    @RequestMapping(value = "/{authClientId}", method = GET)
    public String getById(@PathVariable("authClientId") String authClientId, Model model) {


        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(authClientId);
        model.addAttribute("client", new ClientDetailsResource(clientDetails));

        return "clients/specific";
    }

    @RequestMapping(value = "/{authClientId}/delete", method = GET)
    public String getDeleteConfirmationView(@PathVariable("authClientId") String authClientId, Model model) {

        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(authClientId);
        model.addAttribute("client", new ClientDetailsResource(clientDetails));

        return "clients/confirm_delete";
    }

    @RequestMapping(value = "/{authClientId}", method = DELETE)
    public String deleteClient(@PathVariable("authClientId") String authClientId, RedirectAttributes attributes) {

        jdbcClientDetailsService.removeClientDetails(authClientId);
        attributes.addFlashAttribute("successMessage", "client.delete.success.text");

        return "redirect:/clients";
    }
}
