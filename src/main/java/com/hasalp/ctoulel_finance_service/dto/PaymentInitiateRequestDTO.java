package com.hasalp.ctoulel_finance_service.dto;

import com.hasalp.ctoulel_finance_service.model.PaymentProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentInitiateRequestDTO(

        @NotNull
        Long membreId,

        @NotBlank
        String mois,

        @NotNull
        @Positive
        Double montant,

        @NotNull
        PaymentProvider provider,

        @NotBlank
        String phoneNumber,

        String customerName,

        String customerEmail
) {
}
