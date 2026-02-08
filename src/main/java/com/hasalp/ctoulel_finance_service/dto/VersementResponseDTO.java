package com.hasalp.ctoulel_finance_service.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record VersementResponseDTO(

        Long versementId,
        Long membreId,
        String mois,
        Double montant,
        LocalDateTime dateVersement
) {
}

