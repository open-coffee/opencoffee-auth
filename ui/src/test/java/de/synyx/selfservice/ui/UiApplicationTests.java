package de.synyx.selfservice.ui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UiApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class UiApplicationTests {

    @Value("${local.server.port}")
    private int port;

    @Value("${spring.oauth2.client.userAuthorizationUri}")
    private String authorizeUri;

    private RestTemplate template = new TestRestTemplate();

    @Test
    public void homePageLoads() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void userEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/user", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Session realm=\"JSESSIONID\"",
                response.getHeaders().getFirst("WWW-Authenticate"));
    }

    @Test
    public void resourceEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/resource", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Session realm=\"JSESSIONID\"",
                response.getHeaders().getFirst("WWW-Authenticate"));
    }

    @Test
    public void loginRedirects() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/login", String.class);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        String location = response.getHeaders().getFirst("Location");
        assertTrue("Wrong location: " + location , location.startsWith(authorizeUri));
    }
}