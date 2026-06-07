package com.hasalp.ctoulel_finance_service.validator;

import com.hasalp.ctoulel_finance_service.dto.BirthRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.BirthContributionDTO;
import com.hasalp.ctoulel_finance_service.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class BirthValidator {

    public void validate(BirthRequestDTO dto) {



        // Lieu
        if (dto.lieu() == null || dto.lieu().isBlank()) {
            throw new BusinessException("Le lieu est obligatoire");
        }

        // Nom complet
        if (dto.nomComplet() == null || dto.nomComplet().isBlank()) {
            throw new BusinessException("Le nom complet est obligatoire");
        }

        // Contributions
        if (dto.contributions() == null || dto.contributions().isEmpty()) {
            throw new BusinessException("Au moins une contribution est obligatoire");
        }

        for (BirthContributionDTO contrib : dto.contributions()) {
            if (contrib.membreId() == null || contrib.membreId() <= 0) {
                throw new BusinessException("Identifiant de membre invalide");
            }
            if (contrib.montant() == null || contrib.montant() <= 0) {
                throw new BusinessException("Le montant doit être supérieur à zéro");
            }
        }

        // Doublons dans les membres
        List<Long> membreIds = dto.contributions().stream().map(BirthContributionDTO::membreId).toList();
        if (new HashSet<>(membreIds).size() != membreIds.size()) {
            throw new BusinessException("La liste des contributions contient des doublons de membres");
        }
    }
}