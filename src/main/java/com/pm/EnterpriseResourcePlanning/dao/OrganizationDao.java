package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface OrganizationDao {


    OrganizationEntity saveOrganization(String name, String inn, String address);

    void updateOrganization(String name, String address, UUID id);

    Page<OrganizationEntity> getOrganizationsPage(Specification<OrganizationEntity> specification, Pageable pageable);

    OrganizationEntity getOrganizationById(UUID id);

    boolean existsByInn(String inn);

}
