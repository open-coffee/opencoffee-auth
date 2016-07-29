package coffee.synyx.auth.oauth.user.service;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ldap.core.DirContextAdapter;

import org.springframework.security.ldap.userdetails.LdapAuthority;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import static java.util.Collections.singletonList;


/**
 * Unit test of {@link SynyxUserDetailsContextMapper}.
 *
 * @author  Tobias Schneider
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SynyxUserDetailsContextMapper.class)
public class SynyxUserDetailsContextMapperTest {

    @Autowired
    private SynyxUserDetailsContextMapper sut;

    @Test
    public void mapUserFromContext() throws InvalidNameException {

        String username = "dieserSchneider";
        String password = "sicherHeit";
        String mail = "aus@berlin.de";
        String distinguishName = "uid=schneider,ou=employees,ou=accounts,dc=synyx,dc=coffee";

        DirContextAdapter ctx = new DirContextAdapter();
        ctx.setDn(new LdapName(distinguishName));
        ctx.addAttributeValue("mail", mail);
        ctx.addAttributeValue("userPassword", password);

        LdapAuthority authority = new LdapAuthority("ADMIN", "dn");
        List<LdapAuthority> authorities = singletonList(authority);

        SynyxUserDetails userDetails = (SynyxUserDetails) sut.mapUserFromContext(ctx, username, authorities);
        assertThat(userDetails.getUsername(), is(username));
        assertThat(userDetails.getMail(), is(mail));
        assertThat(userDetails.getPassword(), is(password));
        assertThat(userDetails.getAuthorities(), contains(authority));
        assertThat(userDetails.getDn(), is(distinguishName));
    }
}