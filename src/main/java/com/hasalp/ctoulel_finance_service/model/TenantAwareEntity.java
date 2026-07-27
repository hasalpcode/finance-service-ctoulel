package com.hasalp.ctoulel_finance_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Racine commune de toute entite rattachee a un tenant. tenant_id est
 * NOT NULL des la creation : aucune ligne ne doit exister sans tenant.
 *
 * Le filtre Hibernate automatique (@TenantId + CurrentTenantIdentifierResolver)
 * n'est pas encore active ici - il arrive en Phase 3, une fois le
 * TenantContext propage par le gateway (Phase 2). Jusque-la, aucun code
 * ne peuple encore ce champ : les endpoints de creation de ce service
 * echoueront (contrainte NOT NULL) tant que la Phase 2/3 n'est pas livree -
 * c'est un choix assume plutot qu'une colonne facultative en attendant.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class TenantAwareEntity {

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;
}
