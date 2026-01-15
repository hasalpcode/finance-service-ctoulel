package com.hasalp.ctoulel_finance_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder

public record VersementDTO(
        Long versementId,

        @NotNull
        Long membreId,

        @NotNull
        String mois,

        @NotNull
        Double montant

) {
}

