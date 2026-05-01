package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.ProductDataSource;
import com.pm.EnterpriseResourcePlanning.dto.filters.ProductFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProductRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProductUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
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

    @Transactional
    public ProductResponseDto createProduct(@Valid ProductRequestDto requestDto) {
        return productDataSource.saveProduct(requestDto.name(), requestDto.price(), requestDto.unit(), requestDto.productStatus());
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProductsPage(int page, int size, ProductFilterDto productFilterDto, String sort) {

        Pageable pageable = PageRequest.of(page, size, toProductEntitySort(sort));
        Specification<ProductsEntity> specification =
                ProductsSpecifications.build(productFilterDto.name(), productFilterDto.priceGreater(), productFilterDto.priceLower(),
                        productFilterDto.unitGreater(), productFilterDto.unitLower(), productFilterDto.status());

        return productDataSource.getProductsPage(pageable, specification);
    }

    @Transactional
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

    @Transactional
    public void updateProductUnit(UUID id, Integer unit) {

        productDataSource.updateProductUnit(unit, id);

    }

    public static Sort toProductEntitySort(String sort) {
        if (sort == null) return Sort.by("name").ascending();

        String s = sort.trim().toLowerCase();
        if (s.equals("name,desc")) return Sort.by("name").descending();
        if (s.equals("name,asc")) return Sort.by("name").ascending();

        if (s.equals("price,desc")) return Sort.by("price").descending();
        if (s.equals("price,asc")) return Sort.by("price").ascending();

        if (s.equals("unit,desc")) return Sort.by("unit").descending();
        if (s.equals("unit,asc")) return Sort.by("unit").ascending();


        if (s.equals("status,desc")) return Sort.by("status").descending();
        if (s.equals("status,asc")) return Sort.by("status").ascending();

        return Sort.by("fullName").ascending();
    }
}
