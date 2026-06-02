package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
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
}
