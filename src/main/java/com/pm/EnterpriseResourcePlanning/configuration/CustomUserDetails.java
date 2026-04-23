package com.pm.EnterpriseResourcePlanning.configuration;

import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;
    private final List<SimpleGrantedAuthority> rolesEntities;
    private final UUID id;


    public CustomUserDetails(UserEntity user, List<SimpleGrantedAuthority> rolesEntities, UUID id) {
        this.user = user;
        this.rolesEntities = rolesEntities;
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesEntities;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
