package de.synyx.selfservice.ui.proxy;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Enumeration;

/**
 * Created by klem on 04.05.15.
 */
@Service
public class RequestProxy {

    private static final Logger LOGGER = Logger.getLogger(RequestProxy.class);

    @Autowired
    protected RestTemplate restTemplate;


    public ResponseEntity<String> forwardRequest(URI targetUri, HttpMethod method, HttpServletRequest request) {
        ResponseEntity<String> responseEntity;
        try {
            LOGGER.info("Forward request to " + targetUri.toURL().toString());
            responseEntity = restTemplate.exchange(targetUri, method , getHttpEntity(request), String.class);
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace());
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (HttpClientErrorException e) {
            LOGGER.error(e.getResponseBodyAsString());
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }catch (IOException e){
            LOGGER.error(e.getMessage());
            responseEntity = new ResponseEntity<>("Invalid Request Body:\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    protected HttpEntity<String> getHttpEntity(HttpServletRequest request) throws IOException {
        HttpHeaders headers = getHeaders(request);
        return new HttpEntity<>(IOUtils.toString(request.getInputStream()), headers);
    }

    protected HttpHeaders getHeaders(HttpServletRequest request){
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
