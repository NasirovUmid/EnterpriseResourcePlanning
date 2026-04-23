package com.pm.EnterpriseResourcePlanning.entity;

import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import com.pm.EnterpriseResourcePlanning.utils.FullAuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
public class SalesEntity extends FullAuditEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false, updatable = false)
    private UUID id;

    @JoinColumn(name = "contract_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ContractsEntity contracts;

    @Column(name = "total_price",nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private Instant date;

    private SalesStatus status;

}
