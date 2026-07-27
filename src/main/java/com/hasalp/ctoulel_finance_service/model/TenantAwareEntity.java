package com.hasalp.ctoulel_finance_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;

import java.util.UUID;

/**
 * Racine commune de toute entite rattachee a un tenant. tenant_id est
 * NOT NULL des la creation : aucune ligne ne doit exister sans tenant.
 *
 * @TenantId fait peupler et filtrer automatiquement ce champ par Hibernate
 * (insertion, et "WHERE tenant_id = ?" ajoute sur chaque requete), a partir
 * du tenant courant pose par security.TenantContextFilter sur chaque
 * requete HTTP entrante (voir security.TenantIdentifierResolver).
 */
@MappedSuperclass
@Getter
@Setter
public abstract class TenantAwareEntity {

    @TenantId
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;
}
