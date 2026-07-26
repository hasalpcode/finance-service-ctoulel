package com.hasalp.ctoulel_finance_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "births")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Birth extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long birthId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;


    @Column(nullable = false)
    private String lieu;

    @Column(nullable = false)
    private String nomComplet;

    @OneToMany(mappedBy = "birth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BirthContribution> contributions;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;
}