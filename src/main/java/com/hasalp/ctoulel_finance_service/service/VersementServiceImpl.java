package com.hasalp.ctoulel_finance_service.service;


import com.hasalp.ctoulel_finance_service.dto.RetraitDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementDTO;
import com.hasalp.ctoulel_finance_service.exception.BusinessException;
import com.hasalp.ctoulel_finance_service.exception.ResourceNotFoundException;
import com.hasalp.ctoulel_finance_service.mapper.VersementMapper;
import com.hasalp.ctoulel_finance_service.model.Versement;
import com.hasalp.ctoulel_finance_service.repository.VersementRepository;
import com.hasalp.ctoulel_finance_service.validator.VersementValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VersementServiceImpl implements VersementService {

    private final VersementRepository repository;
    private final VersementMapper mapper;
    private final VersementValidator validator;

    @Override
    public VersementDTO create(VersementDTO dto) {

        if (dto == null) {
            throw new BusinessException("Les données du versement sont obligatoires");
        }

        validator.validate(dto);

//        if (dto.getMontant() > 1_000_000) {
//            throw new BusinessException("Montant de versement trop élevé");
//        }

        Versement versement = mapper.toEntity(dto);
        versement.setDateVersement(LocalDate.now());

        return mapper.toDto(repository.save(versement));

    }

    @Override
    public List<VersementDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public VersementDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("versement introuvable"));
    }

}

