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
@Table(name = "avatars")
@NoArgsConstructor
public class AvatarEntity extends FullAuditEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String url;

    @Column(name = "user_id")
    private UUID UserId;

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (!(o instanceof AvatarEntity avatar)) return false;

        return Objects.equals(avatar.id, this.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
