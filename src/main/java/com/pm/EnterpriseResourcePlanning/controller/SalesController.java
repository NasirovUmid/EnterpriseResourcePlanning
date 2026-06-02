package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.filters.ProductFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProductSalesRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.SalesRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.SalesUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.SalesResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.SalesUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesUseCase salesUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_SALES')")
    public ResponseEntity<SalesResponseDto> saveSales(@Valid @RequestBody SalesRequestDto salesRequestDto) {

        log.info("{}",salesRequestDto);

        SalesResponseDto salesResponseDto = salesUseCase.saveSales(salesRequestDto);
        return ResponseEntity.status(201).body(salesResponseDto);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_SALES')")
    @PostMapping("/products")
    public ResponseEntity<Void> saveProductSales(@Valid @RequestBody List<ProductSalesRequestDto> requestDto) {

        salesUseCase.saveProductSales(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_SALES', 'AUDITOR')")
    public Page<ProductResponseDto> getProductsForSales(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "size") int size,
            @RequestParam(defaultValue = "name,asc", name = "sort") String sort,
            @Valid @ModelAttribute ProductFilterDto productFilterDto
    ) {
        return salesUseCase.getProductsForSales(page, size, productFilterDto, sort);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_SALES', 'AUDITOR')")
    public Page<SalesResponseDto> getSalesPages(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return salesUseCase.getSalesPages(page, size);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_SALES', 'AUDITOR')")
    @GetMapping("/{id}")
    public SalesResponseDto getSalesById(@PathVariable(name = "id") UUID id) {

        return salesUseCase.getSalesById(id);

    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_SALES', 'AUDITOR')")
    public List<ProductResponseDto> getSalesProducts(@PathVariable(name = "id")UUID id){
        return salesUseCase.getSalesProducts(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_SALES')")
    public ResponseEntity<Void> updateSales(@PathVariable(name = "id") UUID id,
                                            @Valid @RequestBody SalesUpdateRequestDto requestDto) {

        log.info("STATUS -------------   {}", requestDto.status());

        salesUseCase.updateSales(id, requestDto.status());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteProductSales(@Valid @RequestBody IntermediateRequestDto requestDto) {

        salesUseCase.deleteProductSales(requestDto);

        return ResponseEntity.ok().build();
    }


}
