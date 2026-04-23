package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ClientType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ClientDao {

    ClientEntity saveClient(String fullname, String phone, ClientType type);

    Page<ClientEntity> getClientPage(Specification<ClientEntity> specification,Pageable pageable);

    void updateClient(String fullname, String phone, UUID id);

    ClientEntity getClientById(UUID id);

}
