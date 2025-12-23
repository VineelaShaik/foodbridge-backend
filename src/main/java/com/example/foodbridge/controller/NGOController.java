package com.example.foodbridge.controller;

import com.example.foodbridge.model.NGO;
import com.example.foodbridge.dto.NGODTO;
import com.example.foodbridge.service.NGOService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ngos")
public class NGOController {

    private final NGOService service;

    public NGOController(NGOService service) {
        this.service = service;
    }

    @PostMapping
    public NGO createNGO(@RequestBody NGODTO dto) {
        return service.createNGO(dto);
    }

    @GetMapping
    public List<NGO> getAll() {
        return service.getAllNGOs();
    }
    @GetMapping("/{id}")
    public ResponseEntity<NGO> getNGOById(@PathVariable Long id) {
        NGO ngo = service.getNGOById(id);
        return ResponseEntity.ok(ngo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NGO> updateNGO(
            @PathVariable Long id,
            @RequestBody NGO updatedNGO) {

        NGO ngo = service.updateNGO(id, updatedNGO);
        return ResponseEntity.ok(ngo);
    }
}
