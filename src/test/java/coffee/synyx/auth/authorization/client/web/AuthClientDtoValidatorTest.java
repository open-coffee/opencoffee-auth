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
public class AuthClientDtoValidatorTest {

    private AuthClientDtoValidator sut;
    private AuthClientDto authClientDto;
    private BindingResult bindingResult;

    @Before
    public void setup() {

        sut = new AuthClientDtoValidator();
        authClientDto = new AuthClientDto();
        bindingResult = new BeanPropertyBindingResult(authClientDto, "authClientDto");
    }


    @Test
    public void supports() {

        assertThat(sut.supports(AuthClientDto.class), is(true));
    }


    @Test
    public void validateFailsIfUriStartsWithFtp() {

        authClientDto.setRegisteredRedirectUri("ftp://test.coffeenet");

        sut.validate(authClientDto, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(true));
        assertThat(bindingResult.hasFieldErrors("registeredRedirectUri"), is(true));
        assertThat(bindingResult.getFieldError("registeredRedirectUri").getCode(),
            is("error.validation.clientdetails.uri"));
    }


    @Test
    public void validateFailsIfUriStartsWithADotAfterScheme() {

        authClientDto.setRegisteredRedirectUri("http://.test.coffeenet");

        sut.validate(authClientDto, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(true));
        assertThat(bindingResult.hasFieldErrors("registeredRedirectUri"), is(true));
        assertThat(bindingResult.getFieldError("registeredRedirectUri").getCode(),
            is("error.validation.clientdetails.uri"));
    }


    @Test
    public void validateFailsIfUriEndsAfterScheme() {

        authClientDto.setRegisteredRedirectUri("http://");

        sut.validate(authClientDto, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(true));
        assertThat(bindingResult.hasFieldErrors("registeredRedirectUri"), is(true));
        assertThat(bindingResult.getFieldError("registeredRedirectUri").getCode(),
            is("error.validation.clientdetails.uri"));
    }


    @Test
    public void validateSucceedsIfUriIsLocal() {

        authClientDto.setRegisteredRedirectUri("http://localhost:9000");

        sut.validate(authClientDto, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(false));
    }


    @Test
    public void validateSucceedsIfUriStartsWithHttp() {

        authClientDto.setRegisteredRedirectUri("http://coffeenet");

        sut.validate(authClientDto, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(false));
    }


    @Test
    public void validateSucceedsIfUriStartsWithHttps() {

        authClientDto.setRegisteredRedirectUri("https://coffeenet");

        sut.validate(authClientDto, bindingResult);

        assertThat(bindingResult.hasFieldErrors(), is(false));
    }
}
