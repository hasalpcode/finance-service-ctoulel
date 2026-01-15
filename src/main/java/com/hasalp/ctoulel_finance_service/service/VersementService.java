package com.hasalp.ctoulel_finance_service.service;


import com.hasalp.ctoulel_finance_service.dto.RetraitDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementDTO;

import java.util.List;

public interface VersementService {

    VersementDTO create(VersementDTO dto);
    List<VersementDTO> getAll();
    VersementDTO getById(Long id);
}
