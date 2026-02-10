package com.hasalp.ctoulel_finance_service.validator;


import com.hasalp.ctoulel_finance_service.dto.RetraitRequestDTO;
import com.hasalp.ctoulel_finance_service.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class RetraitValidator {

    public void validate(RetraitRequestDTO dto) {

        if (dto.caissierId() == null) {
            throw new IllegalArgumentException("Caissier obligatoire");
        }

        if (dto.motif() == null || dto.motif().isBlank()) {
            throw new IllegalArgumentException("Motif obligatoire");
        }

        if (dto.montant() == null || dto.montant() <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }
    }
}

