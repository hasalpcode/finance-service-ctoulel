package com.hasalp.ctoulel_finance_service.paydunya.dto;

public record PayDunyaConfirmResponse(
        String response_code,
        String response_text,
        String status,
        Invoice invoice
) {
    public record Invoice(String token, Double total_amount) {
    }
}
