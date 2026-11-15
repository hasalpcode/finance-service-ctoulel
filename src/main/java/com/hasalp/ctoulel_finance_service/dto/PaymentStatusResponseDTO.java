package com.hasalp.ctoulel_finance_service.dto;

public record PaymentStatusResponseDTO(
        Long transactionId,
        String invoiceToken,
        String status,
        Long versementId
) {
}
