package de.synyx;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.ldap.InitialLdapContext;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by klem on 19.02.15.
 */
public class LdapAuthenticationTest {

    private static String hostUrl;

    private static String username;

    private static String password;


    @BeforeClass
    public static void setUp() throws Exception{
        Properties properties = new Properties();
        properties.load(new FileReader(new File(new File("").getAbsolutePath() + "/src/test/resources/application.properties")));

        hostUrl=properties.getProperty("ldap.hostUrl");
        username=properties.getProperty("test.ldap.user");
        password=properties.getProperty("test.ldap.password");
    }

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
