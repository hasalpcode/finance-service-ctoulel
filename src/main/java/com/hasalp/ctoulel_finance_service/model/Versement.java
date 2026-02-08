package com.hasalp.ctoulel_finance_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "versements")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Versement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long versementId;

    @Column(nullable = false)
    private Long membreId;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    private String mois;


    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateVersement;

}
