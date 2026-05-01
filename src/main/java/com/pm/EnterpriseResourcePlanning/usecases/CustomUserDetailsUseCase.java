package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.configuration.CustomUserDetails;
import com.pm.EnterpriseResourcePlanning.dao.impl.UserDaoImpl;
import com.pm.EnterpriseResourcePlanning.dao.impl.UserRolesDaoImpl;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsUseCase implements UserDetailsService {

    private final UserDaoImpl userDao;
    private final UserRolesDaoImpl userRolesDao;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        UserEntity user = userDao.findUserByUsername(username);
        userRolesDao.findRolesByUserId(user.getId()).forEach(rolesEntity -> authorities.add(new SimpleGrantedAuthority("ROLE_" + rolesEntity.getName())));

        userRolesDao.findAllAuthoritiesByUserId(user.getId()).forEach(s ->
                authorities.add(new SimpleGrantedAuthority(s)));

        return new CustomUserDetails(user,authorities,user.getId());
    }
}
