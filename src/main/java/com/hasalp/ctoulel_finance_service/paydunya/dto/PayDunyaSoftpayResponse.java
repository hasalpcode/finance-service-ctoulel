package com.hasalp.ctoulel_finance_service.paydunya.dto;

import java.util.Map;

public record PayDunyaSoftpayResponse(
        Boolean success,
        String message,
        String url,
        Map<String, String> other_url,
        Object fees,
        String currency
) {
    public boolean isSuccess() {
        return Boolean.TRUE.equals(success);
    }
}
