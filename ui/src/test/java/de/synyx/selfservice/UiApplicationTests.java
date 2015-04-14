package de.synyx.selfservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
	public void contextLoads() {
	}

    @Test
    public void homePageLoads() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void userEndpointNotProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/api/user", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void imgEndpointNotProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/img", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void libEndpointNotProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/lib", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void styleEndpointNotProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/style", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void htmlContentNotProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/someUrl", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Session realm=\"JSESSIONID\"",
                response.getHeaders().getFirst("WWW-Authenticate"));

        response = template.getForEntity("http://localhost:"
                + port + "/someUrl/test.html", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void jsContentNotProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/someUrl", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Session realm=\"JSESSIONID\"",
                response.getHeaders().getFirst("WWW-Authenticate"));

        response = template.getForEntity("http://localhost:"
                + port + "/someUrl/test.js", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void cssContentNotProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/someUrl", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Session realm=\"JSESSIONID\"",
                response.getHeaders().getFirst("WWW-Authenticate"));

        response = template.getForEntity("http://localhost:"
                + port + "/someUrl/test.css", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
