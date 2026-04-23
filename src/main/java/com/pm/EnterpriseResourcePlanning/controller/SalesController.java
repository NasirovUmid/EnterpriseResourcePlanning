package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.SalesRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.SalesResponseDto;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import com.pm.EnterpriseResourcePlanning.usecases.SalesUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesUseCase salesUseCase;

    @PostMapping
    public ResponseEntity<SalesResponseDto> saveSales(@Valid @RequestBody SalesRequestDto salesRequestDto) {

        SalesResponseDto salesResponseDto = salesUseCase.saveSales(salesRequestDto);
        return ResponseEntity.status(201).body(salesResponseDto);
    }

    @PostMapping("/products")
    public ResponseEntity<Void> saveProductSales(@Valid @RequestBody IntermediateRequestDto requestDto) {

        salesUseCase.saveProductSales(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<SalesResponseDto> getSalesPages(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return salesUseCase.getSalesPages(page, size);
    }

    @GetMapping("/{id}")
    public SalesResponseDto getSalesById(@PathVariable(name = "id") UUID id) {

        return salesUseCase.getSalesbyId(id);

    }

    @GetMapping("/products-exist")
    public ResponseEntity<Void> exists(@Valid @RequestBody IntermediateRequestDto requestDto) {

        boolean doesExist = salesUseCase.exists(requestDto);
        return doesExist ? ResponseEntity.ok().build() : ResponseEntity.status(401).build();
    }

    @GetMapping("/products/{id}")
    public List<ProductResponseDto> getSalesProducts(@PathVariable(name = "id")UUID id){
        return salesUseCase.getSalesProducts(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSales(@PathVariable(name = "id") UUID id, SalesStatus status) {

        salesUseCase.updateSales(id, status);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteProductSales(@Valid @RequestBody IntermediateRequestDto requestDto) {

        salesUseCase.deleteProductSales(requestDto);

        return ResponseEntity.ok().build();
    }


}
