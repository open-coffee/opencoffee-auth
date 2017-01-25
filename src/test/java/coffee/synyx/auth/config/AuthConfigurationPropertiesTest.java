package coffee.synyx.auth.config;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;


/**
 * @author  Tobias Schneider
 */
public class AuthConfigurationPropertiesTest {

    @Test
    public void defaultValues() {

        AuthConfigurationProperties sut = new AuthConfigurationProperties();

        assertThat(sut.isDevelopment(), is(true));
        assertThat(sut.getDefaultRedirectUrl(), is("http://localhost:8080"));
        assertThat(sut.getPasswordRecoveryServiceName(), is("profile"));
        assertThat(sut.getPasswordRecoveryPath(), is("/password-recovery"));
    }
}
