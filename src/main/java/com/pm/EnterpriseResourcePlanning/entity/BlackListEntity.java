package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "black_list")
@AllArgsConstructor
@NoArgsConstructor
public class BlackListEntity {

    @Id
    @Column(updatable = false)
    private UUID id;

    private Integer attemptsCount;

    private Instant lockUntil;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof BlackListEntity blackListEntity)) return false;

        return Objects.equals(id, blackListEntity.id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + Objects.hashCode(attemptsCount);
        result = 31 * result + Objects.hashCode(lockUntil);
        return result;
    }
}
