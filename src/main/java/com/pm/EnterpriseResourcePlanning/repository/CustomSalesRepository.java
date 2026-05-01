package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.SalesEntity;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface CustomSalesRepository {

    Optional<SalesEntity> saveSales(UUID contractId, Double totalprice, Instant date, SalesStatus status);


}
