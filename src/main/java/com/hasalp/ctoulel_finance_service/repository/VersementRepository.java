package com.hasalp.ctoulel_finance_service.repository;


import com.hasalp.ctoulel_finance_service.model.Versement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersementRepository extends JpaRepository<Versement, Long> {

    List<Versement> findByMembreIdAndAnnee(Long membreId, Integer annee);
}
