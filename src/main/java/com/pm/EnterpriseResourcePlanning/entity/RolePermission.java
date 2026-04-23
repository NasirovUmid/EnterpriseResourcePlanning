package com.pm.EnterpriseResourcePlanning.entity;
import com.pm.EnterpriseResourcePlanning.utils.AuditEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "role_permissions")
public class RolePermission extends AuditEntity {

    @EmbeddedId
    private RolePermissionId id = new RolePermissionId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RolesEntity roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private PermissionEntity permission;
}
