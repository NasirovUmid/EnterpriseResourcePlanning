package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ContractDaoImpl;
import com.pm.EnterpriseResourcePlanning.dao.impl.SalesDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.SalesDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.SalesResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import com.pm.EnterpriseResourcePlanning.mapper.SalesMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class SalesDataSourceImpl extends MessageAlertDataSource implements SalesDataSource {

    private final SalesDaoImpl salesDao;
    private final SalesMapper salesMapper;

    public SalesDataSourceImpl(AlertSystemDao alertSystemDao, SalesDaoImpl salesDao, SalesMapper salesMapper) {
        super(alertSystemDao, SalesDataSourceImpl.class);
        this.salesDao = salesDao;
        this.salesMapper = salesMapper;
    }

    @Override
    public SalesResponseDto saveSales(UUID contractsId, Double totalprice, Instant date, SalesStatus status) {

        return execute(() -> salesMapper.toDto(salesDao.saveSales(contractsId, totalprice, date, status)));
    }

    @Override
    public Page<SalesResponseDto> getSalesPage(Pageable pageable) {
        return execute(() -> salesDao.getSalesPage(pageable).map(salesMapper::toDto));
    }

    @Override
    public SalesResponseDto getSalesById(UUID id) {
        return execute(() -> salesMapper.toDto(salesDao.getSalesById(id)));
    }

    @Override
    public void updateSales(UUID id, SalesStatus status) {
        execute(() -> salesDao.updateSales(id, status));
    }

}
