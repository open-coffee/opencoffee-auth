package coffee.synyx.auth.view;

import coffee.synyx.auth.AuthenticationServer;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * @author  Tobias Schneider
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AuthenticationServer.class)
@WebIntegrationTest
public class ViewControllerConfigTest {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(springSecurity()).build();
    }


    @Test
    @WithMockUser
    public void login() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/login"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("login"));
        resultActions.andExpect(content().contentType("text/html;charset=UTF-8"));
    }


    @Test
    @WithMockUser
    public void confirm_access() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/oauth/confirm_access"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("confirm_application_access"));
        resultActions.andExpect(content().contentType("text/html;charset=UTF-8"));
    }
}
