package com.example.foodbridge.controller;

import com.example.foodbridge.service.GeoService;
import com.example.foodbridge.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geo")
@RequiredArgsConstructor
public class GeoController {

    private final GeoService geoService;

    @GetMapping("/nearby-ngos")
    public ResponseEntity<?> getNearbyNGOs(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "5") Double radiusKm
    ) {
        return ResponseEntity.ok(geoService.findNearbyNGOs(lat, lng, radiusKm));
    }
}
