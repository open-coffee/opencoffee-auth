package coffee.synyx.auth.oauth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


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

    @ExceptionHandler(value = NoSuchClientException.class)
    public String handleNotFoundException(){

        return "not_found";
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

    @RequestMapping(value = "/{authClientId}/edit", method = GET)
    public String getEditView(@PathVariable("authClientId") String authClientId, Model model) {

        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(authClientId);
        model.addAttribute("client", new ClientDetailsResource(clientDetails));

        return "clients/edit";
    }

    @RequestMapping(value = "/{authClientId}", method = PUT)
    public String updateClient(@PathVariable(value = "authClientId") String authClientId,
                               @Valid @ModelAttribute(value = "client") ClientDetailsResource clientDetailsResource,
                                  BindingResult binding, RedirectAttributes attr){

        if(binding.hasErrors()) {

            return "clients/" + authClientId + "/edit";
        }

        clientDetailsResource.setClientId(authClientId);
        clientDetailsResource.setAutoApprove(true);
        clientDetailsResource.setAuthorizedGrantTypes("authorization_code,password,refresh_token,client_credentials");
        clientDetailsResource.setScope("openid");

        jdbcClientDetailsService.updateClientDetails(clientDetailsResource.toEntity());
        jdbcClientDetailsService.updateClientSecret(authClientId, clientDetailsResource.getClientSecret());
        attr.addFlashAttribute("successMessage", "client.update.success.text");

        return "redirect:/clients";
    }

    @RequestMapping(value = "/new", method = GET)
    public String getNewClientView(Model model) {

        model.addAttribute("client", new ClientDetailsResource());

        return "clients/new";
    }

    @RequestMapping(method = POST)
    public String createNewClient(@Valid @ModelAttribute(value = "client") ClientDetailsResource clientDetailsResource,
                                  BindingResult binding, RedirectAttributes attr){

        if(binding.hasErrors()) {

            return "clients/new";
        }

        clientDetailsResource.setAutoApprove(true);
        clientDetailsResource.setAuthorizedGrantTypes("authorization_code,password,refresh_token,client_credentials");
        clientDetailsResource.setScope("openid");

        jdbcClientDetailsService.addClientDetails(clientDetailsResource.toEntity());
        attr.addFlashAttribute("successMessage", "client.create.success.text");

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
