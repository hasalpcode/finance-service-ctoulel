package com.hasalp.ctoulel_finance_service.mapper;
import com.hasalp.ctoulel_finance_service.dto.VersementDTO;
import com.hasalp.ctoulel_finance_service.model.Versement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VersementMapper {

    VersementDTO toDto(Versement entity);
    Versement toEntity(VersementDTO dto);
}
