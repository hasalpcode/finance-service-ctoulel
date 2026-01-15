package com.hasalp.ctoulel_finance_service.repository;


import com.hasalp.ctoulel_finance_service.model.Versement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersementRepository extends JpaRepository<Versement, Long> {
}
