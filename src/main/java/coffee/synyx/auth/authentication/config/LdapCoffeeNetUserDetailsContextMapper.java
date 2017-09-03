package coffee.synyx.auth.authentication.config;

import org.slf4j.Logger;

import org.springframework.ldap.core.DirContextOperations;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


@Service
public class LdapCoffeeNetUserDetailsContextMapper extends LdapUserDetailsMapper {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    public LdapCoffeeNetUserDetailsContextMapper() {

        super();

        LOGGER.info("//> LdapCoffeeNetUserDetailsContextMapper created");
    }

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
        Collection<? extends GrantedAuthority> authorities) {

        String uid = ctx.getStringAttribute("uid");
        LdapUserDetails ldapUserDetails = (LdapUserDetails) super.mapUserFromContext(ctx, uid, authorities);

        String mail = ctx.getStringAttribute("mail");
        CoffeeNetUserDetails coffeeNetUserDetails = new CoffeeNetUserDetails(ldapUserDetails, mail);
        LOGGER.info("//> Mapped user {} from ldap to CoffeeNetUserDetails", username);
        LOGGER.debug("//> User {} is", coffeeNetUserDetails);

        return coffeeNetUserDetails;
    }
}
