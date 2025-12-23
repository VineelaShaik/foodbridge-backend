package com.example.foodbridge.controller;

import com.example.foodbridge.model.FoodItem;
import com.example.foodbridge.dto.FoodItemDTO;
import com.example.foodbridge.service.FoodItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/food-items")
public class FoodItemController {

    private final FoodItemService service;

    public FoodItemController(FoodItemService service) {
        this.service = service;
    }

    @PostMapping
    public FoodItem create(@RequestBody FoodItemDTO dto) {
        return service.createFoodItem(dto);
    }

    @GetMapping
    public List<FoodItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FoodItem getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public FoodItem update(@PathVariable Long id, @RequestBody FoodItemDTO dto) {
        return service.updateFoodItem(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteFoodItem(id);
    }
}
