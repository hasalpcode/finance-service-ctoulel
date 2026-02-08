package com.hasalp.ctoulel_finance_service.validator;

import com.hasalp.ctoulel_finance_service.dto.VersementRequestDTO;
import com.hasalp.ctoulel_finance_service.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class VersementValidator {

    public void validate(VersementRequestDTO dto) {

        // Membres
        if (dto.membreIds() == null || dto.membreIds().isEmpty()) {
            throw new BusinessException("Au moins un membre est obligatoire");
        }

        if (dto.membreIds().stream().anyMatch(id -> id == null || id <= 0)) {
            throw new BusinessException("Identifiant de membre invalide");
        }

        // Doublons
        List<Long> ids = dto.membreIds();
        if (new HashSet<>(ids).size() != ids.size()) {
            throw new BusinessException("La liste des membres contient des doublons");
        }

        // Montant
        if (dto.montant() == null || dto.montant() <= 0) {
            throw new BusinessException("Le montant doit être supérieur à zéro");
        }

        // Mois
        if (dto.mois() == null || dto.mois().isBlank()) {
            throw new BusinessException("Le mois est obligatoire");
        }
    }
}
