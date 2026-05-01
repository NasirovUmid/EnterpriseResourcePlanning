package com.pm.EnterpriseResourcePlanning.entity;

import com.pm.EnterpriseResourcePlanning.utils.FullAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "modules")
@NoArgsConstructor
public class ModuleEntity extends FullAuditEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;
}
