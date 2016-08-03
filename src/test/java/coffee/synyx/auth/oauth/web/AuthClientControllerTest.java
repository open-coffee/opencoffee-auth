package coffee.synyx.auth.oauth.web;

import coffee.synyx.auth.AuthenticationServer;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import javax.servlet.Filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthenticationServer.class)
public class AuthClientControllerTest {

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @MockBean
    private JdbcClientDetailsService jdbcClientDetailsServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(springSecurity(springSecurityFilterChain))
            .build();
    }


    @Test
    public void getAllClientsViewRedirectsToLoginIfNotLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/clients"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser
    public void getAllClientsViewShowClientsViewIfLoggedIn() throws Exception {

        when(jdbcClientDetailsServiceMock.listClientDetails()).thenReturn(new ArrayList<>());

        ResultActions resultActions = mockMvc.perform(get("/clients"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("clients/all"));
        resultActions.andExpect(model().attributeExists("clients"));
        resultActions.andExpect(model().attribute("clients", new ArrayList<>()));
    }


    @Test
    public void getEditViewRedirectsToLoginIfNotLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/clients/myApp/edit"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser(roles = { "EMPLOYEE" })
    public void getEditViewRedirectsToAccessDeniedViewIfLoggedInAsEmployee() throws Exception {

        when(jdbcClientDetailsServiceMock.loadClientByClientId("myApp")).thenReturn(mock(ClientDetails.class));

        ResultActions resultActions = mockMvc.perform(get("/clients/myApp/edit"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/forbidden"));
    }


    @Test
    @WithMockUser(roles = { "COFFEENET-ADMIN" })
    public void getEditViewReturnsEditViewIfLoggedInAsCoffeenetAdmin() throws Exception {

        when(jdbcClientDetailsServiceMock.loadClientByClientId("myApp")).thenReturn(mock(ClientDetails.class));

        ResultActions resultActions = mockMvc.perform(get("/clients/myApp/edit"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("clients/edit"));
        resultActions.andExpect(model().attributeExists("client"));
    }


    @Test
    public void getDeleteConfirmationViewRedirectsToLoginIfNotLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/clients/myApp/delete"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void getDeleteConfirmationViewRedirectsToAccessDeniedViewIfLoggedInAsEmployee() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/clients/myApp/delete"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/forbidden"));
    }


    @Test
    @WithMockUser(roles = { "COFFEENET-ADMIN" })
    public void getDeleteConfirmationViewReturnsDeleteConfirmationViewIfLoggedInAsCoffeenetAdmin() throws Exception {

        when(jdbcClientDetailsServiceMock.loadClientByClientId("myApp")).thenReturn(mock(ClientDetails.class));

        ResultActions resultActions = mockMvc.perform(get("/clients/myApp/delete"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("clients/confirm_delete"));
        resultActions.andExpect(model().attributeExists("client"));
    }


    @Test
    public void getNewClientViewRedirectsToLoginIfNotLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/clients/new"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void getNewClientViewRedirectsToAccessDeniedViewIfLoggedInAsEmployee() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/clients/new"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/forbidden"));
    }


    @Test
    @WithMockUser(roles = { "COFFEENET-ADMIN" })
    public void getNewClientViewReturnsNewClientViewIfLoggedInAsCoffeenetAdmin() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/clients/new"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("clients/new"));
        resultActions.andExpect(model().attributeExists("client"));
    }


    @Test
    public void getClientViewRedirectsToLoginIfNotLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/clients/myApp"));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser
    public void getClientViewReturnsNewClientViewIfLoggedIn() throws Exception {

        when(jdbcClientDetailsServiceMock.loadClientByClientId("myApp")).thenReturn(mock(ClientDetails.class));

        ResultActions resultActions = mockMvc.perform(get("/clients/myApp"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("clients/specific"));
        resultActions.andExpect(model().attributeExists("client"));
    }


    @Test
    public void updateClient() throws Exception {

        // TODO
    }


    @Test
    public void createNewClient() throws Exception {

        // TODO
    }


    @Test
    public void deleteClientRedirectsToLoginIfNotLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(delete("/clients/myApp").with(csrf()));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser(roles = "EMPLOYEE")
    public void deleteClientRedirectsToAccessDeniedViewIfLoggedInAsEmployee() throws Exception {

        ResultActions resultActions = mockMvc.perform(delete("/clients/myApp").with(csrf()));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/forbidden"));
    }


    @Test
    @WithMockUser(roles = { "COFFEENET-ADMIN" })
    public void deleteClientDeletesClientAndReturnsClientsViewIfLoggedInAsCoffeenetAdmin() throws Exception {

        ResultActions resultActions = mockMvc.perform(delete("/clients/myApp").with(csrf()));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(redirectedUrl("/clients"));

        verify(jdbcClientDetailsServiceMock).removeClientDetails("myApp");
    }
}
