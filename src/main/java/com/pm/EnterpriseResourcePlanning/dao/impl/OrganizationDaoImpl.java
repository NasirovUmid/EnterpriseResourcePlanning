package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.OrganizationDao;
import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrganizationDaoImpl implements OrganizationDao {

    private final OrganizationRepository repository;

    @Override
    public OrganizationEntity saveOrganization(String name, String inn, String address) {
        return repository.save(name, inn, address).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateOrganization(String name, String address, UUID id) {
        repository.updateOrganization(name, address, id);
    }

    @Override
    public Page<OrganizationEntity> getOrganizationsPage(Specification<OrganizationEntity> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public OrganizationEntity getOrganizationById(UUID id) {
        return repository.getOrganizationById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.ORGANIZATION_NOT_FOUND, id));
    }

    @Override
    public boolean existsByInn(String inn) {
        return repository.existsByInn(inn);
    }
}
