package de.synyx.selfservice.ui.proxy;


import de.synyx.selfservice.ui.module.Module;
import de.synyx.selfservice.ui.module.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Enumeration;

/**
 * Created by klem on 13.04.15.
 */
@RestController
public class Proxy {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModuleRepository moduleRepository;

    @RequestMapping("/api/user")
    public Principal getUser(Principal user) {
        return user;
    }


    @RequestMapping(value = "/api/proxy/{module}/**")
    public String redirectPost(@RequestBody(required = false) String body, @PathVariable("module") String moduleName,
                               HttpMethod method, HttpServletRequest request, HttpServletResponse response)
            throws URISyntaxException {
        Module module = moduleRepository.findByName(moduleName);

        String requestedUrlPath = request.getServletPath();
        requestedUrlPath = requestedUrlPath.replace("/api/proxy/" + moduleName, "");

        URI targetUri = new URI(module.getProtocol(), null, module.getHost(), module.getPort(),
                module.getUrlPath() + requestedUrlPath, request.getQueryString(),null);

        HttpHeaders headers = getHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(targetUri, method, entity, String.class);

        return responseEntity.getBody();
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
