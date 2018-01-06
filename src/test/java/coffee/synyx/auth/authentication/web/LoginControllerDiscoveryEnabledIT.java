package coffee.synyx.auth.authentication.web;

import coffee.synyx.autoconfigure.discovery.service.AppQuery;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;


/**
 * @author  Yannic Klem - klem@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "coffeenet.discovery.enabled=true")
public class LoginControllerDiscoveryEnabledIT {

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @MockBean
    private CoffeeNetAppService coffeeNetAppService;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(springSecurity(springSecurityFilterChain))
            .build();
    }


    @Test
    public void redirectsToDefaultRedirectUriIfLoggedInAndNoInstanceFound() throws Exception {

        when(coffeeNetAppService.getApps(any(AppQuery.class))).thenReturn(new HashMap<>());

        ResultActions resultActions = mockMvc.perform(get("/login"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("auth/login"));
        resultActions.andExpect(model().attribute("passwordRecoveryUrl", nullValue()));
    }


    @Test
    public void redirectsToDefaultRedirectUriIfLoggedInAndPasswordRecoveryService() throws Exception {

        Map<String, List<CoffeeNetApp>> apps = new HashMap<>();
        apps.put("profile", singletonList(new CoffeeNetApp("profile", "url", emptySet())));
        when(coffeeNetAppService.getApps(any(AppQuery.class))).thenReturn(apps);

        ResultActions resultActions = mockMvc.perform(get("/login"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("auth/login"));
        resultActions.andExpect(model().attribute("passwordRecoveryUrl", endsWith("/password-recovery")));
    }


    @Test
    @WithMockUser
    public void redirectsToDefaultRedirectUriIfLoggedIn() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/login"));
        resultActions.andExpect(status().isFound());
        resultActions.andExpect(redirectedUrl("http://localhost:8080"));
        resultActions.andExpect(model().attribute("passwordRecoveryUrl", nullValue()));
    }
}
