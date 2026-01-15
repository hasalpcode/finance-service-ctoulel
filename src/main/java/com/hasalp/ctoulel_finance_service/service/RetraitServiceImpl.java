package com.hasalp.ctoulel_finance_service.service;


import com.hasalp.ctoulel_finance_service.dto.RetraitDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementDTO;
import com.hasalp.ctoulel_finance_service.exception.ResourceNotFoundException;
import com.hasalp.ctoulel_finance_service.mapper.RetraitMapper;
import com.hasalp.ctoulel_finance_service.model.Retrait;
import com.hasalp.ctoulel_finance_service.repository.RetraitRepository;
import com.hasalp.ctoulel_finance_service.validator.RetraitValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetraitServiceImpl implements RetraitService {

    private final RetraitRepository repository;
    private final RetraitMapper mapper;
    private final RetraitValidator validator;

    @Override
    public RetraitDTO create(RetraitDTO dto) {
        validator.validate(dto);

        Retrait retrait = mapper.toEntity(dto);
        retrait.setDateRetrait(LocalDate.now());

        return mapper.toDto(repository.save(retrait));
    }

    @Override
    public List<RetraitDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public RetraitDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("retrait introuvable"));
    }
}

