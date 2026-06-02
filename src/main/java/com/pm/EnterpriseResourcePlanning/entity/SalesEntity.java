package com.pm.EnterpriseResourcePlanning.entity;

import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import com.pm.EnterpriseResourcePlanning.utils.FullAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "sales")
@NoArgsConstructor
@AllArgsConstructor
public class SalesEntity extends FullAuditEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "contract_id", nullable = false)
    private UUID contractId;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private Instant date;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "sales_status")
    private SalesStatus status;

}
