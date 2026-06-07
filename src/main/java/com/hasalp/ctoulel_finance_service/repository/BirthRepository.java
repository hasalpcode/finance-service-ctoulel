package com.hasalp.ctoulel_finance_service.repository;

import com.hasalp.ctoulel_finance_service.model.Birth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthRepository extends JpaRepository<Birth, Long> {
}