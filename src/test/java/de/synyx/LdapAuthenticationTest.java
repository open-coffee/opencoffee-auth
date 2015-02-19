package de.synyx;

import org.junit.Test;

import javax.naming.Context;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;

/**
 * Created by klem on 19.02.15.
 */
public class LdapAuthenticationTest {

    @Test
    public void ldapAuthenticationIsSuccessful() throws Exception {
        Hashtable<String,String> env = new Hashtable<String,String>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "uid=[YourName],ou=people,dc=theia,dc=localdomain");
        env.put(Context.PROVIDER_URL, "ldap://ldap-r:389/dc=theia,dc=localdomain");
        env.put(Context.SECURITY_CREDENTIALS, "[YourPassword]");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

        InitialLdapContext ctx = new InitialLdapContext(env, null);

    }
}
