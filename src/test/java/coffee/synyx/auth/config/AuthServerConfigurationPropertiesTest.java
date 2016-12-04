package coffee.synyx.auth.config;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;


/**
 * @author  Tobias Schneider
 */
public class AuthServerConfigurationPropertiesTest {

    @Test
    public void defaultValues() {

        AuthServerConfigurationProperties sut = new AuthServerConfigurationProperties();

        assertThat(sut.getDefaultRedirectUrl(), is("localhost:8080"));
        assertThat(sut.isDevelopment(), is(true));
    }
}
