package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ClientType;

import java.util.Optional;
import java.util.UUID;

public interface CustomClientRepository {

    Optional<ClientEntity> saveClient(String fullname, String phone, ClientType type);

}
