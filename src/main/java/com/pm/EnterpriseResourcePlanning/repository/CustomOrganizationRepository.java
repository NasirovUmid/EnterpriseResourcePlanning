package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;

import java.util.Optional;

public interface CustomOrganizationRepository {
    Optional<OrganizationEntity> save(String name, String inn, String address);

}
