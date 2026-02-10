package com.hasalp.ctoulel_finance_service.mapper;
import com.hasalp.ctoulel_finance_service.dto.VersementRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementResponseDTO;
import com.hasalp.ctoulel_finance_service.model.Versement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VersementMapper {

    VersementResponseDTO toDto(Versement entity);

    // Mapping "manuel" pour un membre
    default Versement toEntity(Long membreId, VersementRequestDTO dto) {
        Versement v = new Versement();
        v.setMembreId(membreId);
        v.setMois(dto.mois());
        v.setMontant(dto.montant());
        return v;
    }

    @org.mapstruct.Mapping(target = "membreId", ignore = true)
    void updateEntityFromDto(VersementRequestDTO dto, @MappingTarget Versement entity);
}

