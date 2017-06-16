package coffee.synyx.auth.authorization.client.web;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.oauth2.provider.ClientDetails;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientDetailsResourceTest {

    private ClientDetailsResource sut;

    @Before
    public void setup() {

        AuthClient authClient = new AuthClient();
        authClient.setClientId("clientId");
        authClient.setClientSecret("clientSecret");
        authClient.getRegisteredRedirectUri().add("https://first-app.coffeenet");
        authClient.getRegisteredRedirectUri().add("https://second-app.coffeenet");
        authClient.getScope().add("openid");
        authClient.getAuthorizedGrantTypes().add("password");
        authClient.getAuthorizedGrantTypes().add("authorization_code");

        sut = new ClientDetailsResource(authClient);
    }


    @Test
    public void toEntity() {

        ClientDetails clientDetails = sut.toEntity();

        assertThat(clientDetails.getAccessTokenValiditySeconds(), is(nullValue()));
        assertThat(clientDetails.getRefreshTokenValiditySeconds(), is(nullValue()));
        assertThat(clientDetails.getAdditionalInformation(), is(notNullValue()));
        assertThat(clientDetails.getAdditionalInformation().size(), is(0));
        assertThat(clientDetails.getAuthorities(), is(notNullValue()));
        assertThat(clientDetails.getAuthorities().size(), is(0));
        assertThat(clientDetails.getAuthorizedGrantTypes(), is(notNullValue()));
        assertThat(clientDetails.getAuthorizedGrantTypes().size(), is(2));
        assertThat(clientDetails.getAuthorizedGrantTypes().contains("password"), is(true));
        assertThat(clientDetails.getAuthorizedGrantTypes().contains("authorization_code"), is(true));
        assertThat(clientDetails.getResourceIds(), is(notNullValue()));
        assertThat(clientDetails.getResourceIds().size(), is(0));
        assertThat(clientDetails.getScope(), is(notNullValue()));
        assertThat(clientDetails.getScope().size(), is(1));
        assertThat(clientDetails.getScope().contains("openid"), is(true));
        assertThat(clientDetails.getRegisteredRedirectUri(), is(notNullValue()));
        assertThat(clientDetails.getRegisteredRedirectUri().size(), is(2));
        assertThat(clientDetails.getRegisteredRedirectUri().contains("https://first-app.coffeenet"), is(true));
        assertThat(clientDetails.getRegisteredRedirectUri().contains("https://second-app.coffeenet"), is(true));
        assertThat(clientDetails.getClientId(), is("clientId"));
        assertThat(clientDetails.getClientSecret(), is("clientSecret"));
        assertThat(clientDetails.isAutoApprove("openid"), is(true));
        assertThat(clientDetails.isScoped(), is(true));
        assertThat(clientDetails.isSecretRequired(), is(true));
    }


    @Test
    public void setRegisteredRedirectedUriBeautifiesString() {

        sut.setRegisteredRedirectUri(",https://first-app.coffeenet , https://second-app.coffeenet,, , ");

        assertThat(sut.getRegisteredRedirectUri(), is("https://first-app.coffeenet,https://second-app.coffeenet"));
    }
}