package com.hasalp.ctoulel_finance_service.controller;


import com.hasalp.ctoulel_finance_service.dto.RetraitDTO;
import com.hasalp.ctoulel_finance_service.service.RetraitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retraits")
@RequiredArgsConstructor
public class RetraitController {

    private final RetraitService service;

    @PostMapping
    public RetraitDTO create(@RequestBody RetraitDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<RetraitDTO> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RetraitDTO findById(@PathVariable Long id) {
        return service.getById(id);
    }
}

