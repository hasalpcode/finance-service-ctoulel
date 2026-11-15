package com.hasalp.ctoulel_finance_service.paydunya.dto;

public record OrangeMoneySenegalRequest(
        String customer_name,
        String customer_email,
        String phone_number,
        String invoice_token
) {
}
