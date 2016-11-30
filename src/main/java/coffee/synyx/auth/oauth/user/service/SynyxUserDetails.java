package coffee.synyx.auth.oauth.user.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import java.util.Collection;


public final class SynyxUserDetails implements LdapUserDetails {

    private static final long serialVersionUID = 8870805981125727355L;

    private final String mail;
    private final String dn;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String username;
    private final String password;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;

    public SynyxUserDetails(LdapUserDetails details, String mail) {

        this.mail = mail;
        this.dn = details.getDn();
        this.enabled = details.isEnabled();
        this.authorities = details.getAuthorities();
        this.username = details.getUsername();
        this.password = details.getPassword();
        this.accountNonExpired = details.isAccountNonExpired();
        this.accountNonLocked = details.isAccountNonLocked();
        this.credentialsNonExpired = details.isCredentialsNonExpired();
    }

    public String getMail() {

        return mail;
    }


    @Override
    public boolean isEnabled() {

        return enabled;
    }


    @Override
    public String getDn() {

        return dn;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }


    @Override
    public String getPassword() {

        return password;
    }


    @Override
    public String getUsername() {

        return username;
    }


    @Override
    public boolean isAccountNonExpired() {

        return accountNonExpired;
    }


    @Override
    public boolean isAccountNonLocked() {

        return accountNonLocked;
    }


    @Override
    public boolean isCredentialsNonExpired() {

        return credentialsNonExpired;
    }
}
