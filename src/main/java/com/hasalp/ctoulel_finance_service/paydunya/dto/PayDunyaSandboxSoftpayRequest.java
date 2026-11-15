package com.hasalp.ctoulel_finance_service.paydunya.dto;

public record PayDunyaSandboxSoftpayRequest(
        String phone_phone,
        String customer_email,
        String password,
        String invoice_token
) {
}
