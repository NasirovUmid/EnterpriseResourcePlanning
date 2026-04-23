package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class ProductSalesId {

    private UUID productId;
    private UUID salesId;
}
