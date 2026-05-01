package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserDataSource {

    UserResponseDto createUser(String fullName, String userName, String password, String phoneNumber);

    Page<UserResponseDto> getUsersPage(Specification<UserEntity> specification, Pageable pageable);

    void updateUser(UUID id, String fullName,String phoneNumber);

    void deactivateUser(UUID uuid);

    UserResponseDto getUserById(UUID id);

    boolean existsByUsername(String username);

    UserResponseDto findUserEntitiesByUsername(String username);

    void updateUserPassword( UUID id, String password);

}
