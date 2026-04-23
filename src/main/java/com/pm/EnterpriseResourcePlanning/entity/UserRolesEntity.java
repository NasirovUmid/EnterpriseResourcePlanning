package com.pm.EnterpriseResourcePlanning.entity;

import com.pm.EnterpriseResourcePlanning.utils.AuditEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRolesEntity extends AuditEntity {

    @EmbeddedId
    private UserRolesId userRolesId = new UserRolesId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RolesEntity roles;

}
