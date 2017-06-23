package coffee.synyx.auth.resource.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

import static org.springframework.security.core.authority.AuthorityUtils.authorityListToSet;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
class CoffeeNetAuthenticationDetails {

    private final String mail;
    private final Set<String> authorities;
    private final String username;

    CoffeeNetAuthenticationDetails(String mail, Collection<? extends GrantedAuthority> authorities, String username) {

        this.mail = mail;
        this.authorities = authorityListToSet(authorities);
        this.username = username;
    }

    public String getMail() {

        return mail;
    }


    public String getUsername() {

        return username;
    }


    public Set<String> getAuthorities() {

        return authorities;
    }
}
