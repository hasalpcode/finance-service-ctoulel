package com.hasalp.ctoulel_finance_service.paydunya.dto;

public record WaveSenegalRequest(
        String wave_senegal_fullName,
        String wave_senegal_email,
        String wave_senegal_phone,
        String wave_senegal_payment_token
) {
}
