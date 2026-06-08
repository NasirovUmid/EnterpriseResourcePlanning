package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.ClientDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.ContractClientDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.SortResolver;
import com.pm.EnterpriseResourcePlanning.dto.filters.ClientFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ClientRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractClientRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.LinkRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ClientResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.SortType;
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

    @Transactional(rollbackFor = Exception.class)
    public ClientResponseDto createClient(@Valid ClientRequestDto clientRequestDto) {
        return clientDataSource.saveClient(clientRequestDto.fullName(), clientRequestDto.phone(), clientRequestDto.type());
    }

    @Transactional(readOnly = true)
    public Page<ClientResponseDto> getClientsPage(int page, int size, String sort, ClientFilterDto clientFilterDto) {


        Specification<ClientEntity> specification = ClientSpecification.build(clientFilterDto.fullname(), clientFilterDto.phone(), clientFilterDto.type());

        Sort sort1 = SortResolver.resolver(SortType.CLIENT, sort);

        Pageable pageable = PageRequest.of(page, size, sort1);

        return clientDataSource.getClientPage(specification, pageable);

    }

    @Transactional(rollbackFor = Exception.class)
    public void saveContractClient(@Valid ContractClientRequestDto requestDto) {


        UUID contractId = requestDto.contractId();
        UUID clientId = requestDto.clientId();

        if (contractClientDataSource.exists(contractId, clientId)) {
            throw new AlreadyExistsException(ErrorMessages.CONTRACT_CLIENT_ALREADY_EXISTS, contractId, clientId);
        }
        contractClientDataSource.saveContractClient(contractId, clientId, requestDto.ownershipShare());
    }

    public boolean exists(@Valid LinkRequestDto requestDto) {

        UUID contractId = requestDto.entityId();
        UUID clientId = requestDto.relatedEntityId();

        return contractClientDataSource.exists(contractId, clientId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeContractClient(@Valid LinkRequestDto requestDto) {

        UUID contractId = requestDto.entityId();
        UUID clientId = requestDto.relatedEntityId();

        contractClientDataSource.removeContractClient(contractId, clientId);
    }

    @Transactional(readOnly = true)
    public List<ContractResponseDto> getClientContracts(UUID id) {
        return contractClientDataSource.getClientContracts(id);
    }

    public @Nullable ClientResponseDto getClient(UUID id) {
        return clientDataSource.getClientById(id);
    }
}
