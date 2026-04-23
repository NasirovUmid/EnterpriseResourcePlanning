package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.impl.ProductSalesDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.datasource.impl.SalesDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.SalesRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.SalesResponseDto;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesUseCase {

    private final SalesDataSourceImpl salesDataSource;
    private final ProductSalesDataSourceImpl productSalesDataSource;

    public SalesResponseDto saveSales(@Valid SalesRequestDto salesRequestDto) {
        return salesDataSource.saveSales(salesRequestDto.contractsId(), salesRequestDto.totalPrice(), salesRequestDto.date(), salesRequestDto.status());
    }

    public Page<SalesResponseDto> getSalesPages(int page, int size) {
        return salesDataSource.getSalesPage(PageRequest.of(page, size));
    }

    public SalesResponseDto getSalesbyId(UUID id) {
        return salesDataSource.getSalesById(id);
    }

    public void updateSales(UUID id, SalesStatus status) {
        salesDataSource.updateSales(id, status);
    }

    public void saveProductSales(@Valid IntermediateRequestDto requestDto) {

        if (exists(requestDto)) {
            throw new AlreadyExistsException(ErrorMessages.PRODUCT_SALES_ALREADY_EXISTS, requestDto.uuid(), requestDto.uuid1());
        }

        productSalesDataSource.saveProductSales(requestDto.uuid(), requestDto.uuid1());
    }

    public boolean exists(@Valid IntermediateRequestDto requestDto) {
        return productSalesDataSource.exists(requestDto.uuid(), requestDto.uuid1());
    }

    public void deleteProductSales(@Valid IntermediateRequestDto requestDto) {
        productSalesDataSource.removeProductSales(requestDto.uuid(), requestDto.uuid1());
    }

    public List<ProductResponseDto> getSalesProducts(UUID id) {
        return productSalesDataSource.getSalesProducts(id);
    }
}
