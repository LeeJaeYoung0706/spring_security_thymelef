package com.example.demo.lib;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
public class MemberDetails implements UserDetails {

    private String NAME;
    private String ROLE;
    private String TOKEN;
    private List<SimpleGrantedAuthority> authList;

    public MemberDetails() {
    }

    private MemberDetails(String NAME, String ROLE, String TOKEN, List<SimpleGrantedAuthority> authList) {
        this.NAME = NAME;
        this.ROLE = ROLE;
        this.TOKEN = TOKEN;
        this.authList = authList;
    }

    public static MemberDetails of(String NAME, String ROLE, String TOKEN, List<SimpleGrantedAuthority> authList) {
        return new MemberDetails(NAME , ROLE , TOKEN , authList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authList;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return NAME;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
