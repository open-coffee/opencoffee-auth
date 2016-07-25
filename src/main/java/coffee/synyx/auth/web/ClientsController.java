package coffee.synyx.auth.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@Controller
@RequestMapping(value = "/clients")
public class ClientsController {

    private final JdbcClientDetailsService clientDetailsService;

    @Autowired
    public ClientsController(JdbcClientDetailsService clientDetailsService) {

        this.clientDetailsService = clientDetailsService;
    }

    @RequestMapping(method = GET)
    public String getAllClientsView(Model model) {

        List<ClientDetailsResource> clientDetails = clientDetailsService.listClientDetails()
                .stream()
                .map(ClientDetailsResource::new)
                .collect(Collectors.toList());

        model.addAttribute("clients", clientDetails);

        return "clients/all";
    }


    @RequestMapping(value = "/{clientId}", method = DELETE)
    public String deleteClient(@PathVariable("clientId") String clientId) {

        clientDetailsService.removeClientDetails(clientId);

        return "redirect:/clients";
    }


    @RequestMapping(value = "/{clientId}", method = GET)
    public String getSpecificClientView(@PathVariable("clientId") String clientId, Model model) {

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        model.addAttribute("client", new ClientDetailsResource(clientDetails));

        return "clients/specific";
    }
}
