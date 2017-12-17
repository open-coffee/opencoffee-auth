package coffee.synyx.auth.authorization.client.web;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthClientDtoTest {

    @Test
    public void setRegisteredRedirectedUriBeautifiesString() {

        AuthClientDto sut = new AuthClientDto();
        sut.setRegisteredRedirectUri(",https://first-app.coffeenet , https://second-app.coffeenet,, , ");

        assertThat(sut.getRegisteredRedirectUri(), is("https://first-app.coffeenet,https://second-app.coffeenet"));
    }


    @Test
    public void testDefaultValues() {

        AuthClientDto sut = new AuthClientDto();

        assertThat(sut.getClientId(), is(nullValue()));
        assertThat(sut.getClientSecret(), is(nullValue()));
        assertThat(sut.getRegisteredRedirectUri(), is(nullValue()));
        assertThat(sut.getResourceIds(), is(nullValue()));
        assertThat(sut.getScope(), is(nullValue()));
        assertThat(sut.getAuthorizedGrantTypes(), is(nullValue()));
        assertThat(sut.getAuthorities(), is(""));
        assertThat(sut.getAccessTokenValidity(), is(nullValue()));
        assertThat(sut.getAdditionalInformation().size(), is(0));
    }
}
