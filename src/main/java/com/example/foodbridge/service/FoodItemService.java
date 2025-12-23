package com.example.foodbridge.service;

import com.example.foodbridge.model.FoodItem;
import com.example.foodbridge.model.Restaurant;
import com.example.foodbridge.dto.FoodItemDTO;
import com.example.foodbridge.repository.FoodItemRepository;
import com.example.foodbridge.repository.RestaurantRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

import com.example.foodbridge.repository.DonationRepository;

@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepo;
    private final RestaurantRepository restaurantRepo;


    public FoodItemService(FoodItemRepository foodItemRepo, RestaurantRepository restaurantRepo) {
        this.foodItemRepo = foodItemRepo;
        this.restaurantRepo = restaurantRepo;
       
    }

    @Transactional
    public FoodItem createFoodItem(FoodItemDTO dto) {
        Restaurant restaurant = restaurantRepo.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        FoodItem foodItem = FoodItem.builder()
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .expiryDate(dto.getExpiryDate())
                .status(dto.getStatus())
                .restaurant(restaurant)
                .build();
        FoodItem saved= foodItemRepo.save(foodItem);
        return saved;
    }


    public List<FoodItem> getAll() {
        return foodItemRepo.findAll();
    }

    public FoodItem getById(Long id) {
        return foodItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));
    }

    @Transactional
    public FoodItem updateFoodItem(Long id, FoodItemDTO dto) {
        FoodItem foodItem = getById(id);

        foodItem.setName(dto.getName());
        foodItem.setQuantity(dto.getQuantity());
        foodItem.setExpiryDate(dto.getExpiryDate());
        foodItem.setStatus(dto.getStatus());

        return foodItemRepo.save(foodItem);
    }

    @Transactional
    public void deleteFoodItem(Long id) {
        if (!foodItemRepo.existsById(id)) {
            throw new RuntimeException("Food item not found");
        }
        foodItemRepo.deleteById(id);
    }
}
