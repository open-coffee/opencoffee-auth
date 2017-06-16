package coffee.synyx.auth.authorization.client.web;

import org.junit.Before;
import org.junit.Test;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
public class ClientDetailsResourceValidatorTest {

    private ClientDetailsResourceValidator sut;
    private ClientDetailsResource clientDetailsResource;
    private BindingResult bindingResult;

    @Before
    public void setup() {

        sut = new ClientDetailsResourceValidator();
        clientDetailsResource = new ClientDetailsResource();
        bindingResult = new BeanPropertyBindingResult(clientDetailsResource, "clientDetailsResource");
    }


    @Test
    public void supports() {

        assertThat(sut.supports(ClientDetailsResource.class), is(true));
    }


    @Test
    public void validateFailsIfUriStartsWithFtp() {

        clientDetailsResource.setRegisteredRedirectUri("ftp://test.coffeenet");

        sut.validate(clientDetailsResource, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(true));
        assertThat(bindingResult.hasFieldErrors("registeredRedirectUri"), is(true));
        assertThat(bindingResult.getFieldError("registeredRedirectUri").getCode(),
            is("error.validation.clientdetails.uri"));
    }


    @Test
    public void validateFailsIfUriStartsWithADotAfterScheme() {

        clientDetailsResource.setRegisteredRedirectUri("http://.test.coffeenet");

        sut.validate(clientDetailsResource, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(true));
        assertThat(bindingResult.hasFieldErrors("registeredRedirectUri"), is(true));
        assertThat(bindingResult.getFieldError("registeredRedirectUri").getCode(),
            is("error.validation.clientdetails.uri"));
    }


    @Test
    public void validateFailsIfUriEndsAfterScheme() {

        clientDetailsResource.setRegisteredRedirectUri("http://");

        sut.validate(clientDetailsResource, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(true));
        assertThat(bindingResult.hasFieldErrors("registeredRedirectUri"), is(true));
        assertThat(bindingResult.getFieldError("registeredRedirectUri").getCode(),
            is("error.validation.clientdetails.uri"));
    }


    @Test
    public void validateSucceedsIfUriIsLocal() {

        clientDetailsResource.setRegisteredRedirectUri("http://localhost:9000");

        sut.validate(clientDetailsResource, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(false));
    }


    @Test
    public void validateSucceedsIfUriStartsWithHttp() {

        clientDetailsResource.setRegisteredRedirectUri("http://coffeenet");

        sut.validate(clientDetailsResource, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(false));
    }


    @Test
    public void validateSucceedsIfUriStartsWithHttps() {

        clientDetailsResource.setRegisteredRedirectUri("https://coffeenet");

        sut.validate(clientDetailsResource, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(false));
    }
}
