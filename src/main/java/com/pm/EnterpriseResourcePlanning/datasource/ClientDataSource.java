package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ClientResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ClientType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ClientDataSource {

    ClientResponseDto saveClient(String fullname, String phone, ClientType type);

    Page<ClientResponseDto> getClientPage(Specification<ClientEntity> specification,Pageable pageable);

    void updateClient(String fullname, String phone, UUID id);

    ClientResponseDto getClientById(UUID id);

}
