package de.synyx;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.Context;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;

/**
 * Created by klem on 19.02.15.
 */
public class LdapAuthenticationTest {

    @Value(value = "${ldap.hostUrl}")
    private String hostUrl;

    @Value(value = "${test.ldap.user")
    private String username;

    @Value(value = "${test.ldap.password")
    private String password;

    @Test
    public void ldapAuthenticationIsSuccessful() throws Exception {
        Hashtable<String,String> env = new Hashtable<String,String>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "uid="+username+",ou=people,dc=theia,dc=localdomain");
        env.put(Context.PROVIDER_URL, hostUrl);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

        InitialLdapContext ctx = new InitialLdapContext(env, null);

    }
}
