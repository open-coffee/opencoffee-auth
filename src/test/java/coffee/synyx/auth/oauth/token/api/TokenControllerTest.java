package coffee.synyx.auth.oauth.token.api;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class TokenControllerTest {

    private TokenController sut;

    @Mock
    private ConsumerTokenServices consumerTokenServicesMock;

    @Before
    public void setUp() throws Exception {

        sut = new TokenController(consumerTokenServicesMock);
    }


    @Test
    public void revoke() throws Exception {

        String tokenId = "tokenId";

        ResultActions resultActions = perform(delete("/token/{token}", tokenId));
        resultActions.andExpect(status().isNoContent());

        verify(consumerTokenServicesMock).revokeToken(tokenId);
    }


    private <T> ResultActions perform(RequestBuilder builder) throws Exception {

        return MockMvcBuilders.standaloneSetup(sut).build().perform(builder);
    }
}
