package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserDao {


    UserEntity saveUser(String fullName, String name, String password, UUID avatarId, String phone) throws Exception;

    boolean checkAccess(UUID userId, String moduleName, String actionName);

    void updateUser(UUID id, String fullName, String password, UUID avatarId);

    void deactivateUser(UUID uuid);

    Page<UserEntity> getUsersPage(Specification<UserEntity> specification, Pageable pageable);

    UserEntity getUserById(UUID id);

    boolean existsByUsername(String username);

    UserEntity findUserByUsername(String username);
}
