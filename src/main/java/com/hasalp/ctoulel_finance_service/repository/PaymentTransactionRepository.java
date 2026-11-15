package com.hasalp.ctoulel_finance_service.repository;

import com.hasalp.ctoulel_finance_service.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    Optional<PaymentTransaction> findByInvoiceToken(String invoiceToken);
}
