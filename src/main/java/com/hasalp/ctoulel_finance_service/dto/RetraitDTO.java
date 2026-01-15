package com.hasalp.ctoulel_finance_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder

public record RetraitDTO(
        Long retraitId,
        @NotNull
        Long caissierId,

        @NotNull
        String motif,

        @NotNull
        Double montant

) {
}

