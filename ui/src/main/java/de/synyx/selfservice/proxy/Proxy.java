package de.synyx.selfservice.proxy;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

/**
 * Created by klem on 13.04.15.
 */
@RestController
public class Proxy {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/api/user")
    public String getUser(Principal principal) throws URISyntaxException{
        URI targetUri = new URI("http", null, "localhost", 9999, "/" + "uaa/user", null, null);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(targetUri, HttpMethod.GET, new HttpEntity<String>(""), String.class);
        return responseEntity.getBody();
    }
/*
    @RequestMapping(value = "/api/{module}/**")
    public String redirectPost(@RequestBody(required = false) String body, @PathVariable("module") String module,
                               HttpMethod method, HttpServletRequest request, HttpServletResponse response)
                               throws URISyntaxException {
        URI targetUri = new URI("http", null, "localhost", 8090, "/" + module, request.getQueryString(), null);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(targetUri, method, new HttpEntity<String>(body), String.class);
        return responseEntity.getBody();
    }
    */
}
