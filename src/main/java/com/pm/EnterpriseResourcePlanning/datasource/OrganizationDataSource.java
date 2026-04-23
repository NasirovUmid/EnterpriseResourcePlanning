package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface OrganizationDataSource {

    OrganizationResponseDto saveOrganization(String name, String inn, String address);

    void updateOrganization(String name, String address, UUID id);

    Page<OrganizationResponseDto> getOrganizationsPage(Specification<OrganizationEntity> specification, Pageable pageable);

    OrganizationResponseDto getOrganizationById(UUID id);

    boolean existsByInn(String inn);

}
