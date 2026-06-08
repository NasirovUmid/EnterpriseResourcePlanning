package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.ProductDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.SortResolver;
import com.pm.EnterpriseResourcePlanning.dto.filters.ProductFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProductRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProductUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.SortType;
import com.pm.EnterpriseResourcePlanning.specifications.ProductsSpecifications;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsUseCase {

    private final ProductDataSource productDataSource;

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDto createProduct(@Valid ProductRequestDto requestDto) {
        return productDataSource.saveProduct(requestDto.name(), requestDto.price(), requestDto.unit(), requestDto.productStatus());
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProductsPage(int page, int size, ProductFilterDto productFilterDto, String sort) {

        Specification<ProductsEntity> specification =
                ProductsSpecifications.build(productFilterDto.name(), productFilterDto.priceGreater(), productFilterDto.priceLower(),
                        productFilterDto.unitGreater(), productFilterDto.unitLower(), productFilterDto.status());

        Sort sort1 = SortResolver.resolver(SortType.PRODUCT,sort);

        Pageable pageable = PageRequest.of(page, size, sort1);

        return productDataSource.getProductsPage(pageable, specification);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(@Valid ProductUpdateRequestDto productUpdateRequestDto) {

        if (productUpdateRequestDto == null || (productUpdateRequestDto.name() == null && productUpdateRequestDto.price() == null &&
                productUpdateRequestDto.unit() == null && productUpdateRequestDto.productStatus() == null)) {
            throw new RuntimeException();
        }

        productDataSource.updateProduct(productUpdateRequestDto.name(),
                productUpdateRequestDto.price(), productUpdateRequestDto.unit(), productUpdateRequestDto.productStatus(), productUpdateRequestDto.productId());

    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(UUID productId) {

        return productDataSource.getProductById(productId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProductUnit(UUID id, Integer unit) {

        productDataSource.updateProductUnit(unit, id);

    }

}
