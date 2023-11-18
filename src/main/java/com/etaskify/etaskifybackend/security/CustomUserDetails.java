package com.etaskify.etaskifybackend.security;

import com.etaskify.etaskifybackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the user's role to a Spring Security GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // You can add logic here if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // You can add logic here if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // You can add logic here if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // You can add logic here if needed, such as checking an 'active' field
    }
}


