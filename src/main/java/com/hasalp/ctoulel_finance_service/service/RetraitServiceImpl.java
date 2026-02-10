package com.hasalp.ctoulel_finance_service.service;


import com.hasalp.ctoulel_finance_service.dto.RetraitRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.RetraitResponseDTO;
import com.hasalp.ctoulel_finance_service.exception.ResourceNotFoundException;
import com.hasalp.ctoulel_finance_service.mapper.RetraitMapper;
import com.hasalp.ctoulel_finance_service.model.Retrait;
import com.hasalp.ctoulel_finance_service.repository.RetraitRepository;
import com.hasalp.ctoulel_finance_service.validator.RetraitValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetraitServiceImpl implements RetraitService {

    private final RetraitRepository repository;
    private final RetraitMapper mapper;
    private final RetraitValidator validator;

    // CREATE
    @Override
    public RetraitResponseDTO create(RetraitRequestDTO dto) {

        validator.validate(dto);

        Retrait retrait = mapper.toEntity(dto);

        Retrait saved = repository.save(retrait);

        return mapper.toDto(saved);
    }

    // UPDATE
    @Override
    public RetraitResponseDTO update(Long id, RetraitRequestDTO dto) {

        validator.validate(dto);

        Retrait retrait = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retrait introuvable"));

        mapper.updateEntityFromDto(dto, retrait);


        Retrait updated = repository.save(retrait);

        return mapper.toDto(updated);

    }

    // GET ALL
    @Override
    @Transactional(readOnly = true)
    public List<RetraitResponseDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // GET ONE
    @Override
    @Transactional(readOnly = true)
    public RetraitResponseDTO getById(Long id) {

        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Retrait introuvable"));
    }

    // DELETE
    @Override
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Retrait introuvable");
        }

        repository.deleteById(id);
    }
}

