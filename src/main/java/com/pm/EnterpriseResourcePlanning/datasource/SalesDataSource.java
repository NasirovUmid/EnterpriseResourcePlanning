package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.SalesResponseDto;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface SalesDataSource {

    SalesResponseDto saveSales(UUID contractsId, Double totalprice, Instant date, SalesStatus status);

    Page<SalesResponseDto> getSalesPage(Pageable pageable);

    SalesResponseDto getSalesById(UUID id);

    void updateSales(UUID id, SalesStatus status);
}
