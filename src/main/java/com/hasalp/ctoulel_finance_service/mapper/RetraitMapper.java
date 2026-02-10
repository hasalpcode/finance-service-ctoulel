package com.hasalp.ctoulel_finance_service.mapper;

import com.hasalp.ctoulel_finance_service.dto.RetraitRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.RetraitResponseDTO;
import com.hasalp.ctoulel_finance_service.model.Retrait;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RetraitMapper {

    Retrait toEntity(RetraitRequestDTO dto);

    RetraitResponseDTO toDto(Retrait entity);

    void updateEntityFromDto(RetraitRequestDTO dto,
                             @MappingTarget Retrait entity);
}

