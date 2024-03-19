package com.spring.vault.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.vault.domain.Credentials;
import com.spring.vault.domain.Role;
import com.spring.vault.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final List<Role> roleList;


    public CustomUserDetails(User user, Credentials credentials) {
        this.username = user.getUsername();
        this.roleList = user.getRoles();
        this.password = credentials.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<Role> roles = new ArrayList<>(roleList);
        for (Object object : roles) {
            Role role = mapper.convertValue(object, Role.class);
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
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
