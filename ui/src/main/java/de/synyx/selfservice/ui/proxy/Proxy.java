package de.synyx.selfservice.ui.proxy;


import de.synyx.selfservice.ui.module.Module;
import de.synyx.selfservice.ui.module.ModuleRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Enumeration;

/**
 * Created by klem on 13.04.15.
 */
@RestController
public class Proxy {

    private static final String MODULE_PROXY_ENDPOINT = "/api/proxy/{module}/**";
    private static final String MODULE_NAME="module";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModuleRepository moduleRepository;

    @RequestMapping("/api/user")
    public Principal getUser(Principal user) {
        return user;
    }


    @RequestMapping(value = MODULE_PROXY_ENDPOINT, method = RequestMethod.GET)
    public ResponseEntity<String> proxyGet(@PathVariable(MODULE_NAME) String moduleName, HttpMethod method,
                                           HttpServletRequest request) throws URISyntaxException, IOException {
        return forwardRequestToModule(moduleName, request, method);
    }

    @RequestMapping(value = MODULE_PROXY_ENDPOINT, method = RequestMethod.POST)
    public ResponseEntity<String> proxyPost(@PathVariable(MODULE_NAME) String moduleName, HttpMethod method,
                                            HttpServletRequest request) throws URISyntaxException, IOException {
        return forwardRequestToModule(moduleName, request, method);
    }

    @RequestMapping(value = MODULE_PROXY_ENDPOINT, method = RequestMethod.PUT)
    public ResponseEntity<String> proxyPut(@PathVariable(MODULE_NAME) String moduleName, HttpMethod method,
                                           HttpServletRequest request) throws URISyntaxException, IOException {
        return forwardRequestToModule(moduleName, request, method);
    }

    @RequestMapping(value = MODULE_PROXY_ENDPOINT, method = RequestMethod.DELETE)
    public ResponseEntity<String> proxyDelete(@PathVariable(MODULE_NAME) String moduleName, HttpMethod method,
                                              HttpServletRequest request) throws URISyntaxException, IOException {
        return forwardRequestToModule(moduleName, request, method);
    }

    @RequestMapping(value = MODULE_PROXY_ENDPOINT, method = RequestMethod.HEAD)
    public ResponseEntity<String> proxyHead(@PathVariable(MODULE_NAME) String moduleName, HttpMethod method,
                                            HttpServletRequest request) throws URISyntaxException, IOException {
        return forwardRequestToModule(moduleName, request, method);
    }

    @RequestMapping(value = MODULE_PROXY_ENDPOINT, method = RequestMethod.OPTIONS)
    public ResponseEntity<String> proxyOptions(@PathVariable(MODULE_NAME) String moduleName, HttpMethod method,
                                               HttpServletRequest request) throws URISyntaxException, IOException {
        return forwardRequestToModule(moduleName, request, method);
    }

    @RequestMapping(value = MODULE_PROXY_ENDPOINT, method = RequestMethod.PATCH)
    public ResponseEntity<String> proxyPatch(@PathVariable(MODULE_NAME) String moduleName, HttpMethod method,
                                             HttpServletRequest request) throws URISyntaxException, IOException {
        return forwardRequestToModule(moduleName, request, method);
    }

    @RequestMapping(value = MODULE_PROXY_ENDPOINT, method = RequestMethod.TRACE)
    public ResponseEntity<String> proxyTRACE(@PathVariable(MODULE_NAME) String moduleName, HttpMethod method,
                                             HttpServletRequest request) throws URISyntaxException, IOException {
        return forwardRequestToModule(moduleName, request, method);
    }

    private ResponseEntity<String> forwardRequestToModule(String moduleName, HttpServletRequest request,
                                                          HttpMethod method) throws URISyntaxException, IOException {
        Module module = moduleRepository.findByName(moduleName);

        String requestedUrlPath = request.getServletPath();
        requestedUrlPath = requestedUrlPath.replace("/api/proxy/" + moduleName, "");

        URI targetUri = new URI(module.getProtocol(), null, module.getHost(),
                module.getPort(), module.getUrlPath() + requestedUrlPath, request.getQueryString(),null);

        HttpHeaders headers = getHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(IOUtils.toString(request.getInputStream()), headers);

        return restTemplate.exchange(targetUri, method, entity, String.class);
    }

    private HttpHeaders getHeaders(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            while (headerValues.hasMoreElements()){
                headers.add(headerName, headerValues.nextElement());
            }
        }
        return headers;
    }

}
