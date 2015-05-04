package de.synyx.selfservice.ui.proxy;


import de.synyx.selfservice.ui.module.Module;
import de.synyx.selfservice.ui.module.ModuleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by klem on 13.04.15.
 */
@Service
public class ModuleRequestProxy extends RequestProxy {

    private static final Logger LOGGER = Logger.getLogger(ModuleRequestProxy.class);

    @Autowired
    private ModuleRepository moduleRepository;

    public ResponseEntity<String> forwardRequestToModule(String moduleName, HttpServletRequest request,
                                                              HttpMethod method) {
        Module module = getModule(moduleName);

        URI targetUri;
        try {
            targetUri = getTargetUri(request, module);
        }catch (URISyntaxException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return forwardRequest(targetUri, method, request);
    }

    private Module getModule(String moduleName){
        Module module = moduleRepository.findByName(moduleName);
        if(module == null){
            LOGGER.warn("Could not find module: " + moduleName);
            throw new ModuleNotFoundException(moduleName);
        }
        return module;
    }

    private URI getTargetUri(HttpServletRequest request, Module module) throws URISyntaxException {
        String requestedUrlPath = request.getServletPath();
        requestedUrlPath = requestedUrlPath.replace("/api/proxy/" + module.getName() + "/", "");

        return new URI(module.getProtocol(), null, module.getHost(), module.getPort(),
                module.getUrlPath() + requestedUrlPath, request.getQueryString(),null);
    }
}
