package com.hasalp.ctoulel_finance_service.service;

import com.hasalp.ctoulel_finance_service.dto.BirthRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.BirthResponseDTO;
import com.hasalp.ctoulel_finance_service.dto.BirthContributionDTO;
import com.hasalp.ctoulel_finance_service.exception.BusinessException;
import com.hasalp.ctoulel_finance_service.exception.ResourceNotFoundException;
import com.hasalp.ctoulel_finance_service.mapper.BirthMapper;
import com.hasalp.ctoulel_finance_service.model.Birth;
import com.hasalp.ctoulel_finance_service.model.BirthContribution;
import com.hasalp.ctoulel_finance_service.repository.BirthRepository;
import com.hasalp.ctoulel_finance_service.validator.BirthValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BirthServiceImpl implements BirthService {

    private final BirthRepository repository;
    private final BirthMapper mapper;
    private final BirthValidator validator;

    @Override
    @Transactional
    public BirthResponseDTO create(BirthRequestDTO dto) {

        validator.validate(dto);

        Birth birth = mapper.toEntity(dto);

        List<BirthContribution> contributions = dto.contributions().stream()
                .map(contribDto -> {
                    BirthContribution contrib = mapper.toContributionEntity(contribDto);
                    contrib.setBirth(birth);
                    return contrib;
                })
                .toList();

        birth.setContributions(contributions);

        Birth saved = repository.save(birth);

        return mapper.toDto(saved);
    }

    @Override
    public List<BirthResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public BirthResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Birth introuvable"));
    }

    @Override
    @Transactional
    public BirthResponseDTO update(Long birthId, BirthRequestDTO dto) {

        Birth birth = repository.findById(birthId)
                .orElseThrow(() -> new ResourceNotFoundException("Birth introuvable"));

        validator.validate(dto);

        birth.setLieu(dto.lieu());
        birth.setNomComplet(dto.nomComplet());

        // Supprimer les anciennes contributions
        birth.getContributions().clear();

        // Ajouter les nouvelles
        List<BirthContribution> newContributions = dto.contributions().stream()
                .map(contribDto -> {
                    BirthContribution contrib = mapper.toContributionEntity(contribDto);
                    contrib.setBirth(birth);
                    return contrib;
                })
                .toList();

        birth.getContributions().addAll(newContributions);

        Birth saved = repository.save(birth);

        return mapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        Birth birth = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Birth introuvable"));

        repository.delete(birth);
    }
}