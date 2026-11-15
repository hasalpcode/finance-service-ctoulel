package com.hasalp.ctoulel_finance_service.service;


import com.hasalp.ctoulel_finance_service.dto.VersementRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementResponseDTO;

import java.util.List;

public interface VersementService {

    List<VersementResponseDTO> create(VersementRequestDTO dto);
    List<VersementResponseDTO> getAll();
    VersementResponseDTO getById(Long id);
    List<VersementResponseDTO> getByMembreAndAnnee(Long membreId, Integer annee);
    VersementResponseDTO update(Long versementId, VersementRequestDTO dto);

    void delete(Long versementId);
}
