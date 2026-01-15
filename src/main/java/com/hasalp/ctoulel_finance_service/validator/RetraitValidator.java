package com.hasalp.ctoulel_finance_service.validator;


import com.hasalp.ctoulel_finance_service.dto.RetraitDTO;
import com.hasalp.ctoulel_finance_service.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class RetraitValidator {

    public void validate(RetraitDTO dto) {
        if (dto.caissierId() == null) {
            throw new BusinessException("Caissier obligatoire");

        }

        if (dto.montant() == null || dto.montant() <= 0) {
            throw new BusinessException("Montant invalide");

        }
    }
}

