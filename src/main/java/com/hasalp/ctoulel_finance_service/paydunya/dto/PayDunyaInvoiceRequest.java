package com.hasalp.ctoulel_finance_service.paydunya.dto;

import java.util.Map;

public record PayDunyaInvoiceRequest(
        Invoice invoice,
        Store store,
        Actions actions,
        Map<String, String> custom_data
) {
    public record Invoice(Double total_amount, String description) {
    }

    public record Store(String name) {
    }

    public record Actions(String callback_url, String return_url, String cancel_url) {
    }
}
