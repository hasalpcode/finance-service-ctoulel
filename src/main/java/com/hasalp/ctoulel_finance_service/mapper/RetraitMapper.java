package com.hasalp.ctoulel_finance_service.mapper;

import com.hasalp.ctoulel_finance_service.dto.RetraitDTO;
import com.hasalp.ctoulel_finance_service.model.Retrait;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetraitMapper {

    RetraitDTO toDto(Retrait entity);
    Retrait toEntity(RetraitDTO dto);
}
