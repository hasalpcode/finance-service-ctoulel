package com.hasalp.ctoulel_finance_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RetraitRequestDTO(

        @NotNull
        Long caissierId,

        @NotBlank
        String motif,

        @NotNull
        @Positive
        Double montant

) {
}
