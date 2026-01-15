package com.hasalp.ctoulel_finance_service.service;


import com.hasalp.ctoulel_finance_service.dto.RetraitDTO;

import java.util.List;

public interface RetraitService {

    RetraitDTO create(RetraitDTO dto);
    List<RetraitDTO> getAll();
    RetraitDTO getById(Long id);
}

