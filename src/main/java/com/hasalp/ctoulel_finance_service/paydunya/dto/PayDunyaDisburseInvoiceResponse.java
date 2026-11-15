package com.hasalp.ctoulel_finance_service.paydunya.dto;

public record PayDunyaDisburseInvoiceResponse(
        String response_code,
        String disburse_token
) {
    public boolean isSuccess() {
        return "00".equals(response_code);
    }
}
