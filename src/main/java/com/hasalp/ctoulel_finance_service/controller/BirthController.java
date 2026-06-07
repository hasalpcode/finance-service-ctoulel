package com.hasalp.ctoulel_finance_service.controller;

import com.hasalp.ctoulel_finance_service.dto.BirthRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.BirthResponseDTO;
import com.hasalp.ctoulel_finance_service.service.BirthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/births")
@RequiredArgsConstructor
public class BirthController {

    private final BirthService service;

    @PostMapping
    public BirthResponseDTO create(@RequestBody BirthRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<BirthResponseDTO> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BirthResponseDTO findById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{birthId}")
    public BirthResponseDTO update(@PathVariable Long birthId, @RequestBody BirthRequestDTO dto) {
        return service.update(birthId, dto);
    }

    @DeleteMapping("/{birthId}")
    public void delete(@PathVariable Long birthId) {
        service.delete(birthId);
    }
}