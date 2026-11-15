package com.hasalp.ctoulel_finance_service.paydunya.dto;

public record PayDunyaDisburseSubmitResponse(
        String response_code,
        String response_text,
        String transaction_id
) {
    public boolean isSuccess() {
        return "00".equals(response_code);
    }
}
