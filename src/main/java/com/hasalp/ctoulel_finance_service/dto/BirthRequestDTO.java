package com.hasalp.ctoulel_finance_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record BirthRequestDTO(

        @NotNull
        String lieu,

        @NotNull
        String nomComplet,

        @NotNull @NotEmpty
        List<BirthContributionDTO> contributions
) {
}