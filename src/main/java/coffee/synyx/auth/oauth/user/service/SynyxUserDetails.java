package coffee.synyx.auth.oauth.user.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.authorityListToSet;


public final class SynyxUserDetails implements LdapUserDetails {

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
    @JsonIgnore
    public String getDn() {

        return dn;
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }


    @JsonProperty("authorities")
    private List<String> getAuthoritiesAsStrings() {

        return new ArrayList<>(authorityListToSet(authorities));
    }


    @Override
    @JsonIgnore
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
