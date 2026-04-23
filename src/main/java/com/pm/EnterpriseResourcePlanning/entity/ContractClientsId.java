package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class ContractClientsId {

    private UUID contractId;
    private UUID clientId;
}
