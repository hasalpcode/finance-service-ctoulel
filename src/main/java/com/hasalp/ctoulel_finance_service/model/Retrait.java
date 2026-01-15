package com.hasalp.ctoulel_finance_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "retraits")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Retrait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long retraitId;

//    @Column(nullable = false)
//    private Long bureauId;

    @Column(nullable = false)
    private Long caissierId;

    @Column(nullable = false,length = 300)
    private String motif;

    @Column(nullable = false)
    private Double montant;

    @CreationTimestamp
    @Column(name = "date_retrait", nullable = false,updatable = false)
    private LocalDate dateRetrait;
}

