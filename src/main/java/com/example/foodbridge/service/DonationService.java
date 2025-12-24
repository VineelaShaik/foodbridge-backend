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

@Transactional
public Donation approveRequest(Long requestId, int quantityDonated) {

    Request request = requestRepo.findById(requestId)
        .orElseThrow(() -> new RuntimeException("Request not found"));

    if (request.getStatus() != Request.Status.PENDING) {
        throw new RuntimeException("Request already processed");
    }

    FoodItem foodItem = request.getFoodItem();

    if (foodItem.getQuantity() < quantityDonated) {
        throw new RuntimeException("Insufficient food quantity");
    }

   
    request.setStatus(Request.Status.APPROVED);

   
    foodItem.setQuantity(foodItem.getQuantity() - quantityDonated);
    foodItem.setStatus(FoodItem.Status.DONATED);

   
    Donation donation = Donation.builder()
        .foodItem(foodItem)
        .ngo(request.getNgo())
        .restaurant(foodItem.getRestaurant())
        .quantityDonated(quantityDonated)
        .build();

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
