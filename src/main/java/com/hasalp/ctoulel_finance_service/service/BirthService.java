package com.hasalp.ctoulel_finance_service.service;

import com.hasalp.ctoulel_finance_service.dto.BirthRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.BirthResponseDTO;

import java.util.List;

public interface BirthService {

    BirthResponseDTO create(BirthRequestDTO dto);
    List<BirthResponseDTO> getAll();
    BirthResponseDTO getById(Long id);
    BirthResponseDTO update(Long birthId, BirthRequestDTO dto);
    void delete(Long birthId);
}