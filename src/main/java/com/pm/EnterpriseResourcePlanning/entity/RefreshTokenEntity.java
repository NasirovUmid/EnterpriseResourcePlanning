package com.pm.EnterpriseResourcePlanning.entity;

import com.pm.EnterpriseResourcePlanning.utils.FullAuditEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Table(name = "refresh_tokens")
public class RefreshTokenEntity extends FullAuditEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, unique = true, updatable = false)
    private String token;

    @Column(nullable = false, name = "expiry_date")
    private Instant expiryDate;

    @Override
    public boolean equals(Object o){

        if (this == o) return true;

        if (!(o instanceof RefreshTokenEntity refreshTokenEntity)) return false;

        return Objects.equals(refreshTokenEntity.id,this.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
