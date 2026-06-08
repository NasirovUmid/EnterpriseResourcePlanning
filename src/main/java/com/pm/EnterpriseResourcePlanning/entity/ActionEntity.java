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
@Table(name = "actions")
@AllArgsConstructor
@NoArgsConstructor
public class ActionEntity extends FullAuditEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof ActionEntity actionEntity)) return false;

//        if (o == null || o.getClass() != ActionEntity.class) return false;

        return Objects.equals(actionEntity.id,this.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

}
