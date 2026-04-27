package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface CustomUserRepository {

    Optional<UserEntity> saveUser(String fullName, String name, String password, String phone);
}
