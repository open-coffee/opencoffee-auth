package de.synyx.event;

import de.synyx.selfservice.Selfservice;
import de.synyx.selfservice.event.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URI;
import java.net.URL;

/**
 * Created by klem on 25.02.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Selfservice.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class EventProcessorControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private TestRestTemplate template;
    private HttpEntity<Event> event;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + Selfservice.EVENT_LISTENER_PROCESSOR_CONTROLLER_ENDPOINT + "/Test");
        template = new TestRestTemplate();
        Event event = new Event();
        event.setName("Test");
        this.event = new HttpEntity<Event>(event);
    }

    @Test
    public void postEventTest() throws Exception {
        template.put(base.toURI(),null);
       // assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
