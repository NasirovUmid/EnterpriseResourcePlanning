package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.dao.ContractDao;
import com.pm.EnterpriseResourcePlanning.dao.ProductDao;
import com.pm.EnterpriseResourcePlanning.dao.ProductSalesDao;
import com.pm.EnterpriseResourcePlanning.dao.SalesDao;
import com.pm.EnterpriseResourcePlanning.datasource.ProductDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.ProductSalesDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.SalesDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.SortResolver;
import com.pm.EnterpriseResourcePlanning.dto.filters.ProductFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.filters.SalesFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.LinkRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProductSalesRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.SalesRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.SalesResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.entity.SalesEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import com.pm.EnterpriseResourcePlanning.enums.SortType;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import com.pm.EnterpriseResourcePlanning.specifications.ProductsSpecifications;
import com.pm.EnterpriseResourcePlanning.specifications.SalesSpecification;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesUseCase {

    private final SalesDataSource salesDataSource;
    private final SalesDao salesDao;
    private final ProductSalesDataSource productSalesDataSource;
    private final ProductSalesDao productSalesDao;
    private final ProductDataSource productDataSource;
    private final ContractDao contractDao;
    private final ProductDao productDao;

    @Transactional
    public SalesResponseDto saveSales(@Valid SalesRequestDto salesRequestDto) {

        ContractsEntity contracts = contractDao.getContractById(salesRequestDto.contractsId());

        if (contracts.getAmount() < salesRequestDto.totalPrice()) {
            throw new BadRequestException();
        }
        return salesDataSource.saveSales(salesRequestDto.contractsId(), salesRequestDto.totalPrice(), salesRequestDto.date(), salesRequestDto.status());
    }

    @Transactional(readOnly = true)
    public Page<SalesResponseDto> getSalesPages(int page, int size, SalesFilterDto salesFilterDto, String sort) {

        Specification<SalesEntity> specification = SalesSpecification.build(salesFilterDto);

        Sort sort1 = SortResolver.resolver(SortType.SALES, sort);

        Pageable pageable = PageRequest.of(page, size, sort1);

        return salesDataSource.getSalesPage(pageable, specification);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProductsForSales(int page, int size, ProductFilterDto productFilterDto, String sort) {
        Specification<ProductsEntity> specification =
                ProductsSpecifications.build(productFilterDto.name(), productFilterDto.priceGreater(), productFilterDto.priceLower(),
                        productFilterDto.unitGreater(), productFilterDto.unitLower(), productFilterDto.status());

        Sort sort1 = SortResolver.resolver(SortType.PRODUCT, sort);

        return productDataSource.getProductsPage(PageRequest.of(page, size, sort1), specification);
    }

    @Transactional(readOnly = true)
    public SalesResponseDto getSalesById(UUID id) {
        return salesDataSource.getSalesById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSales(UUID id, SalesStatus status) {
        salesDataSource.updateSales(id, status);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveProductSales(@Valid List<ProductSalesRequestDto> requestDto) {

        Set<UUID> ids = requestDto.stream().map(ProductSalesRequestDto::productId).collect(Collectors.toSet());
        Map<UUID, ProductsEntity> products = productSalesDao.findAll(ids).stream().collect(Collectors.toMap(ProductsEntity::getId, product -> product));

        SalesEntity sales = salesDao.getSalesById(requestDto.getFirst().salesId());
        ContractsEntity contracts = contractDao.getContractById(sales.getContractId());

        double totalPrice = 0.0;
        for (ProductSalesRequestDto productSalesRequestDto : requestDto) {

            if (productSalesDataSource.exists(productSalesRequestDto.productId(), productSalesRequestDto.salesId())) {
                throw new AlreadyExistsException(ErrorMessages.PRODUCT_SALES_ALREADY_EXISTS, productSalesRequestDto.productId(), productSalesRequestDto.salesId());
            }

            ProductsEntity product = products.get(productSalesRequestDto.productId());

            if (product.getUnit() < productSalesRequestDto.quantity()) {
                throw new BadRequestException();
            }
            totalPrice += product.getPrice() * productSalesRequestDto.quantity();

            if (totalPrice > contracts.getAmount() || totalPrice > sales.getTotalPrice()) {
                throw new BadRequestException();
            }
            productSalesDataSource.saveProductSales(product.getId(), productSalesRequestDto.salesId(), productSalesRequestDto.quantity(), totalPrice);
            productDao.updateProductUnit(productSalesRequestDto.quantity(), product.getId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProductSales(@Valid LinkRequestDto requestDto) {

        UUID productId = requestDto.entityId();
        UUID salesId = requestDto.relatedEntityId();

        productSalesDataSource.removeProductSales(productId, salesId);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getSalesProducts(UUID id) {
        return productSalesDataSource.getSalesProducts(id);
    }
}
