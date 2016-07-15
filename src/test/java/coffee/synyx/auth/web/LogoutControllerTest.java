package coffee.synyx.auth.web;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AuthenticationServer.class)
@WebIntegrationTest
public class LogoutControllerTest {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(springSecurity()).build();
    }


    @Test
    @WithMockUser
    public void returnsLogoutPageIfLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/logout"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("logout"));
    }


    @Test
    public void redirectsToDefaultRedirectUriIfNotLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/logout"));
        resultActions.andExpect(status().isFound());
        resultActions.andExpect(redirectedUrl("https://synyx.coffee"));
    }
}
