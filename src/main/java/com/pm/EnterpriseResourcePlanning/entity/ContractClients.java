package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contract_clients")
public class ContractClients {

    @EmbeddedId
    private ContractClientsId contractClientsId = new ContractClientsId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contractId")
    @JoinColumn(name = "contract_id")
    private ContractsEntity contracts;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clientId")
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @Column(nullable = false)
    private Integer ownershipShare;

}
