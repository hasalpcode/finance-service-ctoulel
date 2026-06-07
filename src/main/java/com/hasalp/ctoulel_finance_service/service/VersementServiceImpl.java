package com.hasalp.ctoulel_finance_service.service;


import com.hasalp.ctoulel_finance_service.dto.VersementRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementResponseDTO;
import com.hasalp.ctoulel_finance_service.exception.BusinessException;
import com.hasalp.ctoulel_finance_service.exception.ResourceNotFoundException;
import com.hasalp.ctoulel_finance_service.mapper.VersementMapper;
import com.hasalp.ctoulel_finance_service.model.Versement;
import com.hasalp.ctoulel_finance_service.repository.VersementRepository;
import com.hasalp.ctoulel_finance_service.validator.VersementValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VersementServiceImpl implements VersementService {

    private final VersementRepository repository;
    private final VersementMapper mapper;
    private final VersementValidator validator;

    @Override
    @Transactional
    public List<VersementResponseDTO> create(VersementRequestDTO dto) {

        if (dto == null || dto.membreIds().isEmpty()) {
            throw new BusinessException("La liste des membres est obligatoire");
        }

        validator.validate(dto);

        LocalDateTime now = LocalDateTime.now();

        List<Versement> versements = dto.membreIds().stream()
                .map(membreId -> {
                    Versement v = mapper.toEntity(membreId, dto);
//                    v.setDateVersement(now);
                    return v;
                })
                .toList();

        List<Versement> saved = repository.saveAll(versements);

        return saved.stream()
                .map(mapper::toDto)
                .toList();
    }



    @Override
    public List<VersementResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public VersementResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("versement introuvable"));
    }


    @Transactional
    public VersementResponseDTO update(Long versementId, VersementRequestDTO dto) {

        Versement versement = repository.findById(versementId)
                .orElseThrow(() -> new ResourceNotFoundException("Versement introuvable"));

        mapper.updateEntityFromDto(dto, versement);

        return mapper.toDto(versement);
    }




    @Override
    public void delete(Long id) {
        Versement versement = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Versement introuvable"));

        repository.delete(versement);
    }

}

