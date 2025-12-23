package com.example.foodbridge.controller;

import com.example.foodbridge.model.Restaurant;
import com.example.foodbridge.model.User;
import com.example.foodbridge.repository.UserRepository;
import com.example.foodbridge.service.RestaurantService;
import com.example.foodbridge.dto.RestaurantDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService service;


    public RestaurantController(RestaurantService service, UserRepository userRepo) {
        this.service = service;
    }

    @PostMapping
    public Restaurant createRestaurant(@RequestBody RestaurantDTO dto) {
        return service.createRestaurant(dto);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAllRestaurants();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant rest = service.getRestaurantById(id);
        return ResponseEntity.ok(rest);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Long id,
            @RequestBody Restaurant updatedRestaurant) {

        Restaurant rest = service.updateRestaurant(id, updatedRestaurant);
        return ResponseEntity.ok(rest);
    }


}
