package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserDao {


    UserEntity saveUser(String fullName, String name, String password, String phone) throws Exception;

    void updateUser(UUID id, String fullName,String phoneNumber);

    void deactivateUser(UUID uuid);

    Page<UserEntity> getUsersPage(Specification<UserEntity> specification, Pageable pageable);

    UserEntity getUserById(UUID id);

    boolean existsByUsername(String username);

    UserEntity findUserByUsername(String username);

    void updateUserPassword(UUID id, String password);
}
