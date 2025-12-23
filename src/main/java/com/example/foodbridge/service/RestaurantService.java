package com.example.foodbridge.service;

import com.example.foodbridge.model.Restaurant;
import com.example.foodbridge.model.User;
import com.example.foodbridge.dto.RestaurantDTO;
import com.example.foodbridge.repository.RestaurantRepository;
import com.example.foodbridge.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepo;
    private final UserRepository userRepo;

    public RestaurantService(RestaurantRepository restaurantRepo, UserRepository userRepo) {
        this.restaurantRepo = restaurantRepo;
        this.userRepo = userRepo;
    }

    public Restaurant createRestaurant(RestaurantDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = Restaurant.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .contactNumber(dto.getContactNumber())
                .user(user)
                .latitude(12.90 + Math.random() * 0.2)
                .longitude(80.10 + Math.random() * 0.2)
                .build();

        return restaurantRepo.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }
     public Restaurant getRestaurantById(Long id) {
        return restaurantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }
@PutMapping("{id}")
public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
    Restaurant restaurant = restaurantRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));

    restaurant.setName(updatedRestaurant.getName());
    restaurant.setAddress(updatedRestaurant.getAddress());
    restaurant.setContactNumber(updatedRestaurant.getContactNumber());
    
    // If restaurant has user mapping
    if (updatedRestaurant.getUser() != null) {
        restaurant.setUser(updatedRestaurant.getUser());
    }

    return restaurantRepo.save(restaurant);
}

}
