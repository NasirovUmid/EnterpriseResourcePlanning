package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.filters.ProductFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProductRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProductUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.ProductsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsUseCase productsUseCase;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {

        ProductResponseDto productResponseDto = productsUseCase.createProduct(requestDto);

        return ResponseEntity.status(201).body(productResponseDto);
    }

    @GetMapping
    public Page<ProductResponseDto> getProductPage(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "size") int size,
            @RequestParam(defaultValue = "name,asc", name = "sort") String sort,
            @Valid @RequestBody ProductFilterDto productFilterDto
    ) {
        return productsUseCase.getProductsPage(page, size, productFilterDto, sort);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {

        productsUseCase.updateProduct(productUpdateRequestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable(name = "id") UUID id) {

        return productsUseCase.getProductById(id);
    }

    @PatchMapping("/{id}/unit")
    public ResponseEntity<Void> updateProductUnit(@PathVariable(name = "id") UUID id, Integer unit) {

        productsUseCase.updateProductUnit(id, unit);

        return ResponseEntity.ok().build();
    }
}
