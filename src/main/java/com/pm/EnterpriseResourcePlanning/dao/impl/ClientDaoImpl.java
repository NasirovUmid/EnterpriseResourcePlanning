package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ClientDao;
import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ClientType;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientDaoImpl implements ClientDao {

    private final ClientRepository repository;

    @Override
    public ClientEntity saveClient(String fullname, String phone, ClientType type) {
        return repository.saveClient(fullname, phone, type).orElseThrow(RuntimeException::new);
    }

    @Override
    public Page<ClientEntity> getClientPage(Specification<ClientEntity> specification,Pageable pageable) {
        return repository.findAll(specification,pageable);
    }

    @Override
    public void updateClient(String fullname, String phone, UUID id) {
         repository.updateClient(fullname, phone, id);
    }

    @Override
    public ClientEntity getClientById(UUID id) {
        return repository.findClientEntityById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.CLIENT_NOT_FOUND,id));
    }
}
