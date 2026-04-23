package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.UserDao;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserDaoImpl implements UserDao {

    private final UserRepository repository;

    @Override
    public UserEntity saveUser(String fullName, String name, String password, UUID avatarId, String phone) {
        return repository.saveUser(fullName, name, password, avatarId, phone).orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean checkAccess(UUID userId, String moduleName, String actionName) {
        return repository.checkAccess(userId, moduleName, actionName);
    }

    @Override
    public void updateUser(UUID id, String fullName, String password, UUID avatarId) {
        repository.updateUser(id, fullName, password, avatarId);
    }

    @Override
    public void deactivateUser(UUID uuid) {
        repository.deactivateUser(uuid);
    }

    @Override
    public Page<UserEntity> getUsersPage(Specification<UserEntity> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public UserEntity getUserById(UUID id) {
        return repository.getUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND, id));
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return repository.findUserEntityByUsername(username).orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND, username));
    }


}
