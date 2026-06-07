package com.hasalp.ctoulel_finance_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record BirthResponseDTO(

        Long birthId,
        LocalDateTime date,
        String lieu,
        String nomComplet,
        List<BirthContributionDTO> contributions,
        LocalDateTime dateCreation
) {
}