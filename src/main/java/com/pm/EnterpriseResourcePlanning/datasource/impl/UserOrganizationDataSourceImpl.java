package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.UserOrganizationDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.UserOrganizationDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserOrganizationDataSourceImpl extends MessageAlertDataSource implements UserOrganizationDataSource {

    private final UserMapper userMapper;
    private final UserOrganizationDaoImpl userOrganizationDao;

    public UserOrganizationDataSourceImpl(AlertSystemDao alertSystemDao, UserMapper userMapper, UserOrganizationDaoImpl userOrganizationDao) {
        super(alertSystemDao, UserOrganizationDataSourceImpl.class);
        this.userMapper = userMapper;
        this.userOrganizationDao = userOrganizationDao;
    }

    @Override
    public void saveUserOrganization(UUID userId, UUID organizationId) {
        execute(() -> userOrganizationDao.saveUserOrganization(userId, organizationId));
    }

    @Override
    public boolean exists(UUID userId, UUID organizationId) {
        return execute(() -> userOrganizationDao.exists(userId, organizationId));
    }

    @Override
    public void removeUserOrganizationLink(UUID userId, UUID organizationId) {
        execute(() -> userOrganizationDao.removeUserOrganizationLink(userId, organizationId));
    }

    @Override
    public List<UserResponseDto> getOrganizationUsers(UUID organizationId) {
        return execute(() -> userOrganizationDao.getOrganizationUsers(organizationId).stream().map(userMapper::toDto).toList());
    }
}
