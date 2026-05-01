package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.UserDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.UserDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDataSourceImpl extends MessageAlertDataSource implements UserDataSource {

    private final UserDaoImpl userDao;
    private final UserMapper userMapper;

    public UserDataSourceImpl(AlertSystemDao alertSystemDao, UserDaoImpl userDao, UserMapper userMapper) {
        super(alertSystemDao, UserRoleDataSourceImpl.class);
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDto createUser(String fullName, String userName, String password, String phoneNumber) {

        return execute(() -> userMapper.toDto(userDao.saveUser(fullName, userName, password, phoneNumber)));
    }

    @Override
    public Page<UserResponseDto> getUsersPage(Specification<UserEntity> specification, Pageable pageable) {
        return execute(() -> userDao.getUsersPage(specification, pageable).map(userMapper::toDto));
    }
    @Override
    public void updateUser(UUID id, String fullName, String phoneNumber) {
        execute(() -> userDao.updateUser(id, fullName, phoneNumber));
    }

    @Override
    public void deactivateUser(UUID uuid) {
        execute(() -> userDao.deactivateUser(uuid));
    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        return execute(() -> userMapper.toDto(userDao.getUserById(id)));
    }

    @Override
    public boolean existsByUsername(String username) {
        return execute(() -> userDao.existsByUsername(username));
    }

    @Override
    public UserResponseDto findUserEntitiesByUsername(String username) {
        return execute(() -> userMapper.toDto(userDao.findUserByUsername(username)));
    }

    @Override
    public void updateUserPassword(UUID id, String password) {
        execute(() -> userDao.updateUserPassword(id, password));
    }
}
