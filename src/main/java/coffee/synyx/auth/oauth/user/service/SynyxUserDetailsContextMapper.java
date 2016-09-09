package coffee.synyx.auth.oauth.user.service;

import org.springframework.ldap.core.DirContextOperations;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class SynyxUserDetailsContextMapper extends LdapUserDetailsMapper {

    public SynyxUserDetailsContextMapper() {

        super();
    }

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
        Collection<? extends GrantedAuthority> authorities) {

        UserDetails details = super.mapUserFromContext(ctx, username, authorities);

        return new SynyxUserDetails((LdapUserDetails) details, ctx.getStringAttribute("mail"));
    }
}
