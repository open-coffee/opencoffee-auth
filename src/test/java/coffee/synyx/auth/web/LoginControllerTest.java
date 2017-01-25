package coffee.synyx.auth.web;

import coffee.synyx.auth.AuthenticationServer;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;
import org.springframework.cloud.netflix.eureka.InstanceInfoFactory;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringEndsWith.endsWith;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


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

    @MockBean
    private DiscoveryClient discoveryClient;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(springSecurity(springSecurityFilterChain))
                .build();
    }


    @Test
    public void redirectsToDefaultRedirectUriIfLoggedInAndNoInstanceFound() throws Exception {

        when(discoveryClient.getInstances(any())).thenReturn(emptyList());

        ResultActions resultActions = mockMvc.perform(get("/login"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("auth/login"));
        resultActions.andExpect(model().attribute("passwordRecoveryUrl", nullValue()));
    }


    @Test
    public void redirectsToDefaultRedirectUriIfLoggedInAndWrongProfileFound() throws Exception {

        when(discoveryClient.getInstances(any())).thenReturn(singletonList(
                new DefaultServiceInstance("profile", "host", 1, true)));

        ResultActions resultActions = mockMvc.perform(get("/login"));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(view().name("auth/login"));
        resultActions.andExpect(model().attribute("passwordRecoveryUrl", nullValue()));
    }


    @Test
    public void redirectsToDefaultRedirectUriIfLoggedInAndPasswordRecoveryService() throws Exception {

        InstanceInfo instanceInfo = new InstanceInfoFactory().create(new MyDataCenterInstanceConfig());
        EurekaServiceInstance eurekaServiceInstance = mock(EurekaServiceInstance.class);
        when(eurekaServiceInstance.getInstanceInfo()).thenReturn(instanceInfo);

        when(discoveryClient.getInstances(any())).thenReturn(singletonList(eurekaServiceInstance));

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
