package com.example.foodbridge.service;

import com.example.foodbridge.model.Donation;
import com.example.foodbridge.repository.DonationRepository;
import com.example.foodbridge.repository.FoodItemRepository;
import com.example.foodbridge.repository.NGORepository;
import com.example.foodbridge.repository.RestaurantRepository;


import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class DonationService {

    private final DonationRepository donationRepo;
    private final FoodItemRepository foodItemRepo;
    private final NGORepository ngoRepo;
    private final RestaurantRepository restaurantRepo;
    private final GeoService geoService;

    public DonationService(DonationRepository donationRepo, FoodItemRepository foodItemRepo,
                           NGORepository ngoRepo, RestaurantRepository restaurantRepo,GeoService geoService) {
        this.donationRepo = donationRepo;
        this.foodItemRepo = foodItemRepo;
        this.ngoRepo = ngoRepo;
        this.restaurantRepo = restaurantRepo;
        this.geoService=geoService;
    }


    public List<Donation> getAll() {
        return donationRepo.findAll();
    }
     public Donation getDonationById(Long id) {
        return donationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
    }
      
}
