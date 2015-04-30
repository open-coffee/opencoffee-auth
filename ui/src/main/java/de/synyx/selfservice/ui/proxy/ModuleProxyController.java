package de.synyx.selfservice.ui.proxy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;

/**
 * Created by klem on 30.04.15.
 */
@RestController
@RequestMapping(value = "/api/proxy/{module}/**")
public class ModuleProxyController {

    private static final String MODULE_NAME="module";

    private Logger logger = Logger.getLogger(ModuleProxyController.class);

    @Autowired
    private ModuleRequestProxy moduleRequestProxy;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> proxyGet(Principal user, @PathVariable(MODULE_NAME) String moduleName,
                                           HttpServletRequest request) throws URISyntaxException, IOException {
        logger.info("Recieved GET-Request for Module: " + moduleName + " from User: " + user.getName());
        return moduleRequestProxy.forwardRequestToModule(moduleName, request, HttpMethod.GET);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> proxyPost(Principal user, @PathVariable(MODULE_NAME) String moduleName,
                                            HttpServletRequest request) throws URISyntaxException, IOException {
        logger.info("Recieved POST-Request for Module: " + moduleName + " from User: " + user.getName());
        return moduleRequestProxy.forwardRequestToModule(moduleName, request, HttpMethod.POST);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> proxyPut(Principal user, @PathVariable(MODULE_NAME) String moduleName,
                                           HttpServletRequest request) throws URISyntaxException, IOException {
        logger.info("Recieved PUT-Request for Module: " + moduleName + " from User: " + user.getName());
        return moduleRequestProxy.forwardRequestToModule(moduleName, request, HttpMethod.PUT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> proxyDelete(Principal user, @PathVariable(MODULE_NAME) String moduleName,
                                              HttpServletRequest request) throws URISyntaxException, IOException {
        logger.info("Recieved DELETE-Request for Module: " + moduleName + " from User: " + user.getName());
        return moduleRequestProxy.forwardRequestToModule(moduleName, request, HttpMethod.DELETE);
    }

    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<String> proxyHead(Principal user, @PathVariable(MODULE_NAME) String moduleName,
                                            HttpServletRequest request) throws URISyntaxException, IOException {
        logger.info("Recieved HEAD-Request for Module: " + moduleName + " from User: " + user.getName());
        return moduleRequestProxy.forwardRequestToModule(moduleName, request, HttpMethod.HEAD);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<String> proxyOptions(Principal user, @PathVariable(MODULE_NAME) String moduleName,
                                               HttpServletRequest request) throws URISyntaxException, IOException {
        logger.info("Recieved OPTIONS-Request for Module: " + moduleName + " from User: " + user.getName());
        return moduleRequestProxy.forwardRequestToModule(moduleName, request, HttpMethod.OPTIONS);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<String> proxyPatch(Principal user, @PathVariable(MODULE_NAME) String moduleName,
                                             HttpServletRequest request) throws URISyntaxException, IOException {
        logger.info("Recieved PATCH-Request for Module: " + moduleName + " from User: " + user.getName());
        return moduleRequestProxy.forwardRequestToModule(moduleName, request, HttpMethod.PATCH);
    }

    @RequestMapping(method = RequestMethod.TRACE)
    public ResponseEntity<String> proxyTRACE(Principal user, @PathVariable(MODULE_NAME) String moduleName,
                                             HttpServletRequest request) throws URISyntaxException, IOException {
        logger.info("Recieved TRACE-Request for Module: " + moduleName + " from User: " + user.getName());
        return moduleRequestProxy.forwardRequestToModule(moduleName, request, HttpMethod.TRACE);
    }
}
