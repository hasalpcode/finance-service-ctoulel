package com.hasalp.ctoulel_finance_service.controller;


import com.hasalp.ctoulel_finance_service.dto.RetraitDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementDTO;
import com.hasalp.ctoulel_finance_service.service.VersementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/versements")
@RequiredArgsConstructor
public class VersementController {

    private final VersementService service;

    @PostMapping
    public VersementDTO create(@RequestBody VersementDTO dto) {
        return service.create(dto);
    }


    @GetMapping
    public List<VersementDTO> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public VersementDTO findById(@PathVariable Long id) {
        return service.getById(id);
    }
}
