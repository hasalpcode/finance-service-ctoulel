package com.hasalp.ctoulel_finance_service.paydunya.dto;

public record PayDunyaInvoiceResponse(
        String response_code,
        String response_text,
        String token,
        String description
) {
    public boolean isSuccess() {
        return "00".equals(response_code);
    }
}
