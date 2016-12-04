package coffee.synyx.auth.web;

import coffee.synyx.auth.AuthenticationServer;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthenticationServer.class)
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(springSecurity(springSecurityFilterChain))
            .build();
    }


    @Test
    public void loginReturnsLoginPageIfNotLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/login"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("auth/login"));
    }


    @Test
    @WithMockUser
    public void redirectsToDefaultRedirectUriIfLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/login"));
        resultActions.andExpect(status().isFound());
        resultActions.andExpect(redirectedUrl("http://localhost:8080"));
    }
}
