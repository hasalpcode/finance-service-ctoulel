package com.hasalp.ctoulel_finance_service.controller;


import com.hasalp.ctoulel_finance_service.dto.RetraitRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.RetraitResponseDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementResponseDTO;
import com.hasalp.ctoulel_finance_service.service.RetraitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retraits")
@RequiredArgsConstructor
public class RetraitController {

    private final RetraitService service;

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetraitResponseDTO create(@Valid @RequestBody RetraitRequestDTO dto) {
        return service.create(dto);
    }

    // GET ALL
    @GetMapping
    public List<RetraitResponseDTO> findAll() {
        return service.getAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public RetraitResponseDTO findById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public RetraitResponseDTO update(@PathVariable Long id, @RequestBody RetraitRequestDTO dto) {
        return service.update(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
