package com.hasalp.ctoulel_finance_service.validator;


import com.hasalp.ctoulel_finance_service.dto.VersementDTO;
import org.springframework.stereotype.Component;

@Component
public class VersementValidator {

    public void validate(VersementDTO dto) {
        if (dto.membreId() == null) {
            throw new IllegalArgumentException("Membre obligatoire");
        }
        if (dto.montant() == null || dto.montant() <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }
    }
}
