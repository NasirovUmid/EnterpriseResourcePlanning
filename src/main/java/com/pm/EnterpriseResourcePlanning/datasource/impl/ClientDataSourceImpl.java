package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ClientDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.ClientDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ClientResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ClientType;
import com.pm.EnterpriseResourcePlanning.mapper.ClientMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientDataSourceImpl extends MessageAlertDataSource implements ClientDataSource {

    private final ClientDaoImpl clientDao;
    private final ClientMapper clientMapper;

    public ClientDataSourceImpl(AlertSystemDao alertSystemDao, ClientDaoImpl clientDao, ClientMapper clientMapper) {
        super(alertSystemDao, ClientDataSourceImpl.class);
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientResponseDto saveClient(String fullname, String phone, ClientType type) {
        return execute(() -> clientMapper.toDto(clientDao.saveClient(fullname, phone, type)));
    }

    @Override
    public Page<ClientResponseDto> getClientPage(Specification<ClientEntity> specification,Pageable pageable) {
        return execute(() -> clientDao.getClientPage(specification,pageable).map(clientMapper::toDto));
    }

    @Override
    public void updateClient(String fullname, String phone, UUID id) {
        execute(()->clientDao.updateClient(fullname, phone, id));
    }

    @Override
    public ClientResponseDto getClientById(UUID id) {
        return execute(() -> clientMapper.toDto(clientDao.getClientById(id)));
    }
}