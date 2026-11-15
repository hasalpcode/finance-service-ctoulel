package com.hasalp.ctoulel_finance_service.dto;

public record PaymentInitiateResponseDTO(
        Long transactionId,
        String invoiceToken,
        String checkoutUrl,
        String omUrl,
        String maxitUrl,
        String status
) {
}
