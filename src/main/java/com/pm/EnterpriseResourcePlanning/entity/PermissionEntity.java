package com.pm.EnterpriseResourcePlanning.entity;

import com.pm.EnterpriseResourcePlanning.utils.FullAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
public class PermissionEntity extends FullAuditEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column (name = "module_id", nullable = false)
    private UUID moduleId;

    @Column(name = "action_id", nullable = false)
    private UUID actionId;

    @Override
    public boolean equals(Object o){

        if (this == o) return true;

        if (!(o instanceof PermissionEntity permissionEntity)) return false;

        return Objects.equals(this.id,permissionEntity.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
