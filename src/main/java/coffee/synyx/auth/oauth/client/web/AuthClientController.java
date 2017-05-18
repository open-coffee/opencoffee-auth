package coffee.synyx.auth.oauth.client.web;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
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

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Yannic Klem - klem@synyx.de
 */
@Controller
@RequestMapping("/clients")
public class AuthClientController {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String REDIRECT_CLIENTS = "redirect:/clients";
    private static final String OAUTH_CLIENTS_NEW = "oauth/clients/new";
    private static final String CLIENT = "client";

    private JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    public AuthClientController(JdbcClientDetailsService jdbcClientDetailsService) {

        this.jdbcClientDetailsService = jdbcClientDetailsService;
    }

    @ExceptionHandler(value = NoSuchClientException.class)
    public String handleNotFoundException() {

        LOGGER.info("//> Clients: A not registered client was requested.");

        return "oauth/clients/not_found";
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

        LOGGER.info("//> Clients: Provide overview to all clients");

        return "oauth/clients/all";
    }


    @RequestMapping(value = "/{authClientId}/edit", method = GET)
    public String getEditView(@PathVariable("authClientId") String authClientId, Model model) {

        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(authClientId);
        model.addAttribute(CLIENT, new ClientDetailsResource(clientDetails));

        LOGGER.info("//> Clients: Provide client edit page of client {}", authClientId);

        return "oauth/clients/edit";
    }


    @RequestMapping(value = "/{authClientId}", method = PUT)
    public String updateClient(@PathVariable(value = "authClientId") String authClientId,
        @Valid
        @ModelAttribute(value = CLIENT)
        ClientDetailsResource clientDetailsResource, BindingResult binding, RedirectAttributes attr) {

        clientDetailsResource.setClientId(authClientId);

        if (binding.hasErrors()) {
            attr.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + CLIENT, binding);
            attr.addFlashAttribute(CLIENT, clientDetailsResource);

            LOGGER.info("//> Clients: Could not edit {} client, because of binding errors", authClientId);

            return "oauth/clients/edit";
        }

        jdbcClientDetailsService.updateClientDetails(clientDetailsResource.toEntity());
        jdbcClientDetailsService.updateClientSecret(authClientId, clientDetailsResource.getClientSecret());
        attr.addFlashAttribute(SUCCESS_MESSAGE, "client.update.success.text");

        LOGGER.info("//> Clients: Successful edited client {}", authClientId);

        return REDIRECT_CLIENTS;
    }


    @RequestMapping(value = "/new", method = GET)
    public String getNewClientView(Model model) {

        model.addAttribute(CLIENT, new ClientDetailsResource());

        LOGGER.info("//> Clients: Provide create new client page");

        return OAUTH_CLIENTS_NEW;
    }


    @RequestMapping(method = POST)
    public String createNewClient(@Valid
        @ModelAttribute(value = CLIENT)
        ClientDetailsResource clientDetailsResource, BindingResult binding, RedirectAttributes attr) {

        if (binding.hasErrors()) {
            return OAUTH_CLIENTS_NEW;
        }

        try {
            jdbcClientDetailsService.addClientDetails(clientDetailsResource.toEntity());

            attr.addFlashAttribute(SUCCESS_MESSAGE, "client.create.success.text");

            LOGGER.debug("//> Clients: Client {} created", clientDetailsResource.getClientId());

            return REDIRECT_CLIENTS;
        } catch (ClientAlreadyExistsException e) {
            LOGGER.debug("//> Clients: Client already exists", e);

            binding.rejectValue("clientId", "error.client.creation.id.alreadyexists");

            attr.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + CLIENT, binding);
            attr.addFlashAttribute(CLIENT, clientDetailsResource);

            return OAUTH_CLIENTS_NEW;
        }
    }


    @RequestMapping(value = "/{authClientId}", method = GET)
    public String getClientView(@PathVariable("authClientId") String authClientId, Model model) {

        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(authClientId);
        model.addAttribute(CLIENT, new ClientDetailsResource(clientDetails));

        LOGGER.debug("//> Clients: Provide specific client page of {}", authClientId);

        return "oauth/clients/specific";
    }


    @RequestMapping(value = "/{authClientId}/delete", method = GET)
    public String getDeleteConfirmationView(@PathVariable("authClientId") String authClientId, Model model) {

        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(authClientId);
        model.addAttribute(CLIENT, new ClientDetailsResource(clientDetails));

        LOGGER.debug("//> Clients: Provide client confirmation page to deleted {}", authClientId);

        return "oauth/clients/confirm_delete";
    }


    @RequestMapping(value = "/{authClientId}", method = DELETE)
    public String deleteClient(@PathVariable("authClientId") String authClientId, RedirectAttributes attributes) {

        jdbcClientDetailsService.removeClientDetails(authClientId);
        attributes.addFlashAttribute(SUCCESS_MESSAGE, "client.delete.success.text");

        LOGGER.debug("//> Clients: Client {} deleted", authClientId);

        return REDIRECT_CLIENTS;
    }
}
