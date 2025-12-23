package com.example.foodbridge.service;

import com.example.foodbridge.model.Donation;
import com.example.foodbridge.model.FoodItem;
import com.example.foodbridge.model.NGO;
import com.example.foodbridge.model.Restaurant;
import com.example.foodbridge.dto.DonationDTO;
import com.example.foodbridge.repository.DonationRepository;
import com.example.foodbridge.repository.FoodItemRepository;
import com.example.foodbridge.repository.NGORepository;
import com.example.foodbridge.repository.RestaurantRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

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

    public Donation createDonation(DonationDTO dto) {
        FoodItem foodItem = foodItemRepo.findById(dto.getFoodItemId())
                .orElseThrow(() -> new RuntimeException("FoodItem not found"));

        NGO ngo = ngoRepo.findById(dto.getNgoId())
                .orElseThrow(() -> new RuntimeException("NGO not found"));

        Restaurant restaurant = restaurantRepo.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Donation donation = Donation.builder()
                .foodItem(foodItem)
                .ngo(ngo)
                .restaurant(restaurant)
                .quantityDonated(dto.getQuantityDonated())
                .build();
        foodItem.setQuantity(foodItem.getQuantity()-dto.getQuantityDonated());

        return donationRepo.save(donation);
    }

    public List<Donation> getAll() {
        return donationRepo.findAll();
    }
     public Donation getDonationById(Long id) {
        return donationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
    }
        public Donation autoMatchDonation(FoodItem foodItem) {
        Double lat = foodItem.getRestaurant().getLatitude();
        Double lng = foodItem.getRestaurant().getLongitude();

        // find nearby NGOs within 10 km
        List<Map<String, Object>> nearbyNGOs = geoService.findNearbyNGOs(lat, lng, 10.0);
        if (nearbyNGOs.isEmpty()) {
        throw new RuntimeException("No nearby NGOs found for auto-allocation");
        }

        NGO bestNGO = null;
        double bestScore = -1;

        for (Map<String, Object> ngoData : nearbyNGOs) {
        double distance = (Double) ngoData.get("distance_km");
        int capacity = ((Number) ngoData.get("capacity")).intValue();

        long hoursToExpiry = ChronoUnit.HOURS.between(
                LocalDateTime.now(),
                foodItem.getExpiryDate().atStartOfDay()
        );

            // formula: closer, sooner expiry, higher capacity = higher priority
            double score = (1 / (distance + 0.1)) * 0.5
                    + (1.0 / (hoursToExpiry + 1)) * 0.3
                    + (capacity * 0.2);

        if (score > bestScore) {
                bestScore = score;
                bestNGO = ngoRepo.findById(((Number) ngoData.get("id")).longValue()).orElse(null);
        }
        }

        if (bestNGO == null) {
        throw new RuntimeException("Could not find suitable NGO");
        }

        Donation donation = new Donation();
        donation.setFoodItem(foodItem);
        donation.setNgo(bestNGO);
        donation.setQuantityDonated(foodItem.getQuantity());
        donation.setDonatedAt(LocalDateTime.now());

        return donationRepo.save(donation);
}
}
