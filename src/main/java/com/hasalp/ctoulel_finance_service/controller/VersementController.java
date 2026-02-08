package com.hasalp.ctoulel_finance_service.controller;


import com.hasalp.ctoulel_finance_service.dto.RetraitDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementRequestDTO;
import com.hasalp.ctoulel_finance_service.dto.VersementResponseDTO;
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
    public List<VersementResponseDTO> create(@RequestBody VersementRequestDTO dto) {
        return service.create(dto);
    }



    @GetMapping
    public List<VersementResponseDTO> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public VersementResponseDTO findById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{versementId}")
    public VersementResponseDTO update(@PathVariable Long versementId, @RequestBody VersementRequestDTO dto) {
        return service.update(versementId, dto);
    }


    @DeleteMapping("/{versementId}")
    public void delete(@PathVariable Long versementId) {
        service.delete(versementId);
    }

}
