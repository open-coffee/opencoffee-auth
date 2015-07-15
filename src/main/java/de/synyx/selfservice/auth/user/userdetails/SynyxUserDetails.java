package de.synyx.selfservice.auth.user.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import java.util.Collection;


public class SynyxUserDetails implements LdapUserDetails {

    private static final long serialVersionUID = 1L;

    private String mail;
    private LdapUserDetails details;

    public SynyxUserDetails(LdapUserDetails details) {

        this.details = details;
    }

    public String getMail() {

        return mail;
    }


    public void setMail(String mail) {

        this.mail = mail;
    }


    @Override
    public boolean isEnabled() {

        return details.isEnabled();
    }


    @Override
    public String getDn() {

        return details.getDn();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return details.getAuthorities();
    }


    @Override
    public String getPassword() {

        return details.getPassword();
    }


    @Override
    public String getUsername() {

        return details.getUsername();
    }


    @Override
    public boolean isAccountNonExpired() {

        return details.isAccountNonExpired();
    }


    @Override
    public boolean isAccountNonLocked() {

        return details.isAccountNonLocked();
    }


    @Override
    public boolean isCredentialsNonExpired() {

        return details.isCredentialsNonExpired();
    }
}
