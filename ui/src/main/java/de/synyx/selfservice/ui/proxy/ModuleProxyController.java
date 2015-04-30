package de.synyx.selfservice.ui.proxy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by klem on 30.04.15.
 */
@RestController
public class ModuleProxyController {
    private static final Logger logger = Logger.getLogger(ModuleProxyController.class);

    @Autowired
    private ModuleRequestProxy moduleRequestProxy;

    @RequestMapping(value = "/api/proxy/{module}/**")
    @ResponseBody
    public ResponseEntity<String> proxyGet(Principal user, @PathVariable("module") String moduleName,
                                           HttpMethod method, HttpServletRequest request) {
        String logMessage = String.format("Recieved %s-Request for Module: %s from User: %s",
                method.toString(), moduleName, user.getName());
        logger.info(logMessage);
        return moduleRequestProxy.forwardRequestToModule(moduleName, request, method);
    }
}
