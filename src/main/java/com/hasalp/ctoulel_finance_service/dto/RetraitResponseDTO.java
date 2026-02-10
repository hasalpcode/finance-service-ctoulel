package com.hasalp.ctoulel_finance_service.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record RetraitResponseDTO(
        Long retraitId,
        Long caissierId,
        String motif,
        Double montant,
        LocalDateTime dateRetrait
) {}


