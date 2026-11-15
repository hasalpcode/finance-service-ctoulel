package com.hasalp.ctoulel_finance_service.paydunya.dto;

public record PayDunyaDisburseInvoiceRequest(
        String account_alias,
        int amount,
        String withdraw_mode,
        String callback_url
) {
}
