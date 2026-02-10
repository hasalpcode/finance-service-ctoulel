package com.hasalp.ctoulel_finance_service.service;


import com.hasalp.ctoulel_finance_service.dto.RetraitRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.RetraitResponseDTO;

import java.util.List;

public interface RetraitService {


        RetraitResponseDTO create(RetraitRequestDTO dto);

        RetraitResponseDTO update(Long id, RetraitRequestDTO dto);

        List<RetraitResponseDTO> getAll();

        RetraitResponseDTO getById(Long id);

        void delete(Long id);


}

