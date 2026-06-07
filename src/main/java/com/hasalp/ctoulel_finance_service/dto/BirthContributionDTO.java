package com.hasalp.ctoulel_finance_service.dto;

import jakarta.validation.constraints.NotNull;

public record BirthContributionDTO(

        @NotNull
        Long membreId,

        @NotNull
        Double montant
) {
}