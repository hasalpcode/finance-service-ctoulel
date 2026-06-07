package com.hasalp.ctoulel_finance_service.mapper;

import com.hasalp.ctoulel_finance_service.dto.BirthRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.BirthResponseDTO;
import com.hasalp.ctoulel_finance_service.dto.BirthContributionDTO;
import com.hasalp.ctoulel_finance_service.model.Birth;
import com.hasalp.ctoulel_finance_service.model.BirthContribution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BirthMapper {

    @Mapping(target = "contributions", source = "contributions")
    BirthResponseDTO toDto(Birth entity);

    @Mapping(target = "contributions", ignore = true)
    @Mapping(target = "date", ignore = true)
    Birth toEntity(BirthRequestDTO dto);

    BirthContributionDTO toContributionDto(BirthContribution contribution);

    List<BirthContributionDTO> toContributionDtos(List<BirthContribution> contributions);

    @Mapping(target = "birth", ignore = true)
    BirthContribution toContributionEntity(BirthContributionDTO dto);
}