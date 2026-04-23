package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.SalesDao;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.entity.SalesEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SalesDaoImpl implements SalesDao {

    private final SalesRepository repository;

    @Override
    public SalesEntity saveSales(ContractsEntity contracts, Double totalprice, Instant date, SalesStatus status) {
        return repository.saveSales(contracts, totalprice, date, status).orElseThrow(RuntimeException::new);
    }

    @Override
    public Page<SalesEntity> getSalesPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public SalesEntity getSalesById(UUID id) {
        return repository.getSalesById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.SALES_NOT_FOUND,id));
    }

    @Override
    public void updateSales(UUID id, SalesStatus status) {
        repository.updateSales(id, status);
    }
}
