package coffee.synyx.auth.authentication.config;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.ldap.core.DirContextAdapter;

import org.springframework.security.ldap.userdetails.LdapAuthority;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import static java.util.Collections.singletonList;


/**
 * Unit test of {@link LdapCoffeeNetUserDetailsContextMapper}.
 *
 * @author  Tobias Schneider
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LdapCoffeeNetUserDetailsContextMapper.class)
public class LdapCoffeeNetUserDetailsContextMapperIT {

    @Autowired
    private LdapCoffeeNetUserDetailsContextMapper sut;

    @Test
    public void mapUserFromContext() throws InvalidNameException {

        String username = "uSeRnAme";
        String uid = "username";
        String password = "password";
        String mail = "user@coffeenet";
        String distinguishName = "uid=user,ou=employees,ou=accounts,dc=coffee,dc=net";

        DirContextAdapter ctx = new DirContextAdapter();
        ctx.setDn(new LdapName(distinguishName));
        ctx.addAttributeValue("mail", mail);
        ctx.addAttributeValue("uid", uid);
        ctx.addAttributeValue("userPassword", password);

        LdapAuthority authority = new LdapAuthority("ADMIN", "dn");
        List<LdapAuthority> authorities = singletonList(authority);

        CoffeeNetUserDetails userDetails = (CoffeeNetUserDetails) sut.mapUserFromContext(ctx, username, authorities);
        assertThat(userDetails.getUsername(), is(uid));
        assertThat(userDetails.getMail(), is(mail));
        assertThat(userDetails.getPassword(), is(password));
        assertThat(userDetails.getAuthorities(), contains(authority));
        assertThat(userDetails.getDn(), is(distinguishName));
    }


    @Test
    public void eraseCredentials() throws InvalidNameException {

        DirContextAdapter ctx = new DirContextAdapter();
        ctx.setDn(new LdapName("uid=schneider,ou=employees,ou=accounts,dc=coffee,dc=net"));
        ctx.addAttributeValue("mail", "user@coffeenet");
        ctx.addAttributeValue("userPassword", "passw0rd");
        ctx.addAttributeValue("uid", "username");

        LdapAuthority authority = new LdapAuthority("ADMIN", "dn");
        List<LdapAuthority> authorities = singletonList(authority);

        CoffeeNetUserDetails userDetails = (CoffeeNetUserDetails) sut.mapUserFromContext(ctx, "uSeRnAme", authorities);
        userDetails.eraseCredentials();
        assertThat(userDetails.getPassword(), nullValue());
    }
}
