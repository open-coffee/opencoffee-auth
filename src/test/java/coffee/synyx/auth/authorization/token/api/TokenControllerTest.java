package coffee.synyx.auth.authorization.token.api;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


/**
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class TokenControllerTest {

    private TokenController sut;

    @Mock
    private ConsumerTokenServices consumerTokenServicesMock;

    @Before
    public void setUp() {

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

        return standaloneSetup(sut).build().perform(builder);
    }
}
