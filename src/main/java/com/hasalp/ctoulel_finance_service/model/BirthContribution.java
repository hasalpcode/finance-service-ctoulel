package com.hasalp.ctoulel_finance_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "birth_contributions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BirthContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_id", nullable = false)
    private Birth birth;

    @Column(nullable = false)
    private Long membreId;

    @Column(nullable = false)
    private Double montant;
}