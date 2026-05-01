package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.entity.SalesEntity;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface SalesDao {

    SalesEntity saveSales(UUID contractId, Double totalprice, Instant date, SalesStatus status);

    Page<SalesEntity> getSalesPage(Pageable pageable);

    SalesEntity getSalesById(UUID id);

    void updateSales(UUID id, SalesStatus status);

}
