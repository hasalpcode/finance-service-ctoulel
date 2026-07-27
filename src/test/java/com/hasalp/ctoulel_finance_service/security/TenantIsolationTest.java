package com.hasalp.ctoulel_finance_service.security;

import com.hasalp.ctoulel_finance_service.model.Versement;
import com.hasalp.ctoulel_finance_service.repository.VersementRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Meme principe que le TenantIsolationTest de ctoulel-member-service :
 * verifie que le filtre Hibernate @TenantId isole reellement les tenants.
 * TenantContext doit etre pose AVANT d'ouvrir la transaction, puisque
 * Hibernate resout le tenant courant a la creation de l'EntityManager.
 */
@SpringBootTest
class TenantIsolationTest {

    @Autowired
    private VersementRepository versementRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PersistenceContext
    private EntityManager entityManager;

    private TransactionTemplate tx;

    private final UUID tenantA = UUID.randomUUID();
    private final UUID tenantB = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        this.tx = new TransactionTemplate(transactionManager);
    }

    @AfterEach
    void cleanUp() {
        tx.executeWithoutResult(status -> {
            entityManager.createNativeQuery("DELETE FROM versements WHERE tenant_id IN (UUID_TO_BIN(?1), UUID_TO_BIN(?2))")
                    .setParameter(1, tenantA.toString())
                    .setParameter(2, tenantB.toString())
                    .executeUpdate();
        });
    }

    @Test
    void unTenantNeVoitJamaisLesVersementsDUnAutreTenant() {
        Long versementAId = createVersement(tenantA, 100.0);
        Long versementBId = createVersement(tenantB, 200.0);

        runAsTenant(tenantA, () -> {
            assertThat(versementRepository.findById(versementAId)).isPresent();
            assertThat(versementRepository.findById(versementBId)).isEmpty();

            List<Versement> visibles = versementRepository.findAll();
            assertThat(visibles).extracting(Versement::getVersementId).containsExactly(versementAId);
            return null;
        });
    }

    @Test
    void sansTenantDansLeContexteAucuneLigneNestVisible() {
        createVersement(tenantA, 100.0);

        tx.executeWithoutResult(status -> assertThat(versementRepository.findAll()).isEmpty());
    }

    private Long createVersement(UUID tenantId, double montant) {
        return runAsTenant(tenantId, () -> {
            Versement versement = new Versement();
            versement.setMembreId(1L);
            versement.setMontant(montant);
            versement.setMois("2026-07");
            return versementRepository.save(versement).getVersementId();
        });
    }

    private <T> T runAsTenant(UUID tenantId, Supplier<T> action) {
        TenantContext.set(tenantId);
        try {
            TransactionCallback<T> callback = status -> action.get();
            return tx.execute(callback);
        } finally {
            TenantContext.clear();
        }
    }
}
