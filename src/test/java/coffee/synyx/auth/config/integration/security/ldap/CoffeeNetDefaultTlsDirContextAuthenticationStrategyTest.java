package coffee.synyx.auth.config.integration.security.ldap;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;

import static org.mockito.Mockito.verify;

import static javax.naming.Context.SECURITY_AUTHENTICATION;
import static javax.naming.Context.SECURITY_CREDENTIALS;
import static javax.naming.Context.SECURITY_PRINCIPAL;


/**
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetDefaultTlsDirContextAuthenticationStrategyTest {

    @Mock
    private LdapContext ldapContextMock;

    @Test
    public void applyAuthentication() throws NamingException {

        CoffeeNetDefaultTlsDirContextAuthenticationStrategy sut =
            new CoffeeNetDefaultTlsDirContextAuthenticationStrategy();

        String userDn = "userDnIsThis";
        String password = "maybeASecretPassword";

        sut.applyAuthentication(ldapContextMock, userDn, password);

        verify(ldapContextMock).addToEnvironment(SECURITY_AUTHENTICATION, "simple");
        verify(ldapContextMock).addToEnvironment(SECURITY_PRINCIPAL, userDn);
        verify(ldapContextMock).addToEnvironment(SECURITY_CREDENTIALS, password);
        verify(ldapContextMock).reconnect(null);
    }
}
