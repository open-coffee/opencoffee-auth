package coffee.synyx.auth.web;

import coffee.synyx.auth.AuthenticationServer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;

import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AuthenticationServer.class)
@WebIntegrationTest
public class RootControllerTest {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(springSecurity()).build();
    }


    @Test
    public void redirectToRootIfNotLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser
    public void redirectToRootIfLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("https://synyx.coffee"));
    }
}
