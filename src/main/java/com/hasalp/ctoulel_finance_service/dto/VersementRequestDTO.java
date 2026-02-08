package com.hasalp.ctoulel_finance_service.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;


public record VersementRequestDTO(

        @NotNull
        List<Long> membreIds,

        @NotNull
        String mois,

        @NotNull
        Double montant
) {
}

