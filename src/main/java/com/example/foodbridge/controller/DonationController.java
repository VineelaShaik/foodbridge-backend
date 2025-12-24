package com.example.foodbridge.controller;

import com.example.foodbridge.model.Donation;
import com.example.foodbridge.dto.DonationDTO;
import com.example.foodbridge.service.DonationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {

    private final DonationService service;

    public DonationController(DonationService service) {
        this.service = service;
    }

@GetMapping
public ResponseEntity<List<Donation>> getAll() {
    return ResponseEntity.ok(service.getAll());
}
  @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDonationById(id));
    }

}
