package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.ClientDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.ContractClientDataSource;
import com.pm.EnterpriseResourcePlanning.dto.filters.ClientFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ClientRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractClientRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ClientResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import com.pm.EnterpriseResourcePlanning.specifications.ClientSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientUseCase {

    private final ClientDataSource clientDataSource;
    private final ContractClientDataSource contractClientDataSource;

    @Transactional
    public ClientResponseDto createClient(@Valid ClientRequestDto clientRequestDto) {
        return clientDataSource.saveClient(clientRequestDto.fullName(), clientRequestDto.phone(), clientRequestDto.type());
    }

    @Transactional(readOnly = true)
    public Page<ClientResponseDto> getClientsPage(int page, int size, String sort, ClientFilterDto clientFilterDto) {


        Specification<ClientEntity> specification = ClientSpecification.build(clientFilterDto.fullname(), clientFilterDto.phone(), clientFilterDto.type());

        Pageable pageable = PageRequest.of(page, size, toClientEntitySort(sort));

        return clientDataSource.getClientPage(specification, pageable);

    }

    @Transactional
    public void saveContractClient(@Valid ContractClientRequestDto requestDto) {

        if (contractClientDataSource.exists(requestDto.uuid(), requestDto.uuid1())) {
            throw new AlreadyExistsException(ErrorMessages.CONTRACT_CLIENT_ALREADY_EXISTS, requestDto.uuid(), requestDto.uuid1());
        }

        contractClientDataSource.saveContractClient(requestDto.uuid(), requestDto.uuid1(), requestDto.ownershipShare());
    }

    public boolean exists(@Valid IntermediateRequestDto requestDto) {
        return contractClientDataSource.exists(requestDto.uuid(), requestDto.uuid1());
    }

    @Transactional
    public void removeContractClient(@Valid IntermediateRequestDto requestDto) {
        contractClientDataSource.removeContractClient(requestDto.uuid(), requestDto.uuid1());
    }

    @Transactional(readOnly = true)
    public List<ContractResponseDto> getClientContracts(UUID id) {
        return contractClientDataSource.getClientContracts(id);
    }

    private Sort toClientEntitySort(String sort) {

        if (sort == null || !sort.contains(",")) {
            return Sort.by("fullName,asc");
        }

        String[] parts = sort.split(",");
        String field = parts[0];
        String direction = parts[1];

        return direction.equalsIgnoreCase("desc") ?
                Sort.by(field).descending() :
                Sort.by(field).ascending();
    }

    public @Nullable ClientResponseDto getClient(UUID id) {
        return clientDataSource.getClientById(id);
    }
}
