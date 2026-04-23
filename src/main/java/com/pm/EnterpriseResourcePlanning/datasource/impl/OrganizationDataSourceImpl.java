package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.OrganizationDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.OrganizationDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.mapper.OrganizationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrganizationDataSourceImpl extends MessageAlertDataSource implements OrganizationDataSource {

    private final OrganizationDaoImpl organizationDao;
    private final OrganizationMapper organizationMapper;

    public OrganizationDataSourceImpl(AlertSystemDao alertSystemDao, OrganizationDaoImpl organizationDao, OrganizationMapper organizationMapper) {
        super(alertSystemDao, OrganizationDataSourceImpl.class);
        this.organizationDao = organizationDao;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public OrganizationResponseDto saveOrganization(String name, String inn, String address) {
        return execute(() -> organizationMapper.toDto(organizationDao.saveOrganization(name, inn, address)));
    }

    @Override
    public void updateOrganization(String name, String address, UUID id) {
        execute(() -> organizationDao.updateOrganization(name, address, id));
    }

    @Override
    public Page<OrganizationResponseDto> getOrganizationsPage(Specification<OrganizationEntity> specification, Pageable pageable) {
        return execute(() -> organizationDao.getOrganizationsPage(specification,pageable).map(organizationMapper::toDto));
    }

    @Override
    public OrganizationResponseDto getOrganizationById(UUID id) {
        return execute(() -> organizationMapper.toDto(organizationDao.getOrganizationById(id)));
    }

    @Override
    public boolean existsByInn(String inn) {
        return organizationDao.existsByInn(inn);
    }
}
