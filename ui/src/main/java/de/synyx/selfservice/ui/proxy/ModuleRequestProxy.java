package de.synyx.selfservice.ui.proxy;


import de.synyx.selfservice.ui.module.Module;
import de.synyx.selfservice.ui.module.ModuleRepository;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

/**
 * Created by klem on 13.04.15.
 */
@Service
public class ModuleRequestProxy {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModuleRepository moduleRepository;

    private Logger logger = Logger.getLogger(ModuleRequestProxy.class);

    public ResponseEntity<String> forwardRequestToModule(String moduleName, HttpServletRequest request,
                                                          HttpMethod method) {
        Module module = getModule(moduleName);

        URI targetUri = getTargetUri(request, module);

        HttpEntity<String> entity = getHttpEntity(request);

        return sendRequest(targetUri, method, entity);
    }

    private Module getModule(String moduleName){
        Module module = moduleRepository.findByName(moduleName);
        if(module == null){
            logger.warn("Could not find module: " + moduleName);
            throw new ModuleNotFoundException(moduleName);
        }
        return module;
    }

    private URI getTargetUri(HttpServletRequest request, Module module){
        String requestedUrlPath = request.getServletPath();
        requestedUrlPath = requestedUrlPath.replace("/api/proxy/" + module.getName(), "");
        URI targetUri;
        try{
            targetUri = new URI(module.getProtocol(), null, module.getHost(),
                    module.getPort(), module.getUrlPath() + requestedUrlPath, request.getQueryString(),null);
        }catch (URISyntaxException syntaxException){
            throw  new ModuleURIMalformedException(syntaxException.getMessage());
        }
        return targetUri;
    }

    private HttpEntity<String> getHttpEntity(HttpServletRequest request){
        HttpHeaders headers = getHeaders(request);
        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<>(IOUtils.toString(request.getInputStream()), headers);
        }catch (IOException e){
            throw new ProxyRequestBodyException(e.getMessage());
        }
        return entity;
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

    private ResponseEntity<String> sendRequest(URI targetUri, HttpMethod method, HttpEntity entity){
        ResponseEntity<String> responseEntity;
        try{
            logger.info("Forward request to " + targetUri.toURL().toString());
            responseEntity = restTemplate.exchange(targetUri, method, entity, String.class);
        }catch (MalformedURLException malformedURLException){
            throw new ModuleURIMalformedException(malformedURLException.getMessage());
        }
        catch (HttpClientErrorException e){
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
            return responseEntity;
        }
        return responseEntity;
    }
}
