package com.hasalp.ctoulel_finance_service.repository;


import com.hasalp.ctoulel_finance_service.model.Retrait;
import com.hasalp.ctoulel_finance_service.model.Versement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetraitRepository extends JpaRepository<Retrait, Long> {
}
