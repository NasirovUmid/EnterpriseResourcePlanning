package com.pm.EnterpriseResourcePlanning.entity;

import com.pm.EnterpriseResourcePlanning.utils.FullAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organizations")
public class OrganizationEntity extends FullAuditEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private String inn;

    @Column(nullable = false)
    private String address;

}

