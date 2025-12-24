package com.example.foodbridge.service;

import com.example.foodbridge.dto.RequestDTO;
import com.example.foodbridge.model.*;
import com.example.foodbridge.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RequestService {

    private final RequestRepository requestRepo;
    private final FoodItemRepository foodItemRepo;
    private final NGORepository ngoRepo;
    private final DonationRepository donationRepo;

    public RequestService(RequestRepository requestRepo,
                          FoodItemRepository foodItemRepo,
                          NGORepository ngoRepo,
                          DonationRepository donationRepo) {
        this.requestRepo = requestRepo;
        this.foodItemRepo = foodItemRepo;
        this.ngoRepo = ngoRepo;
        this.donationRepo = donationRepo;
    }

    // ✅ 1. NGO creates a Request (ALWAYS PENDING)
    public Request createRequest(RequestDTO dto) {

        FoodItem foodItem = foodItemRepo.findById(dto.getFoodItemId())
                .orElseThrow(() -> new RuntimeException("FoodItem not found"));

        NGO ngo = ngoRepo.findById(dto.getNgoId())
                .orElseThrow(() -> new RuntimeException("NGO not found"));

        Request request = Request.builder()
                .foodItem(foodItem)
                .ngo(ngo)
                .status(Request.Status.PENDING)
                .requestedAt(LocalDateTime.now())
                .build();

        return requestRepo.save(request);
    }

    // ✅ 2. Get all Requests
    public List<Request> getAllRequests() {
        return requestRepo.findAll();
    }

    // ✅ 3. Get Request by ID
    public Request getRequestById(Long id) {
        return requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    // ✅ 4. APPROVE request → create Donation
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

        // Approve request
        request.setStatus(Request.Status.APPROVED);

        // Update food item
        foodItem.setQuantity(foodItem.getQuantity() - quantityDonated);
        foodItem.setStatus(FoodItem.Status.DONATED);

        // Create donation
        Donation donation = Donation.builder()
                .foodItem(foodItem)
                .ngo(request.getNgo())
                .restaurant(foodItem.getRestaurant())
                .quantityDonated(quantityDonated)
                .donatedAt(LocalDateTime.now())
                .build();

        return donationRepo.save(donation);
    }

    // ✅ 5. Reject request
    public Request rejectRequest(Long requestId) {

        Request request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() != Request.Status.PENDING) {
            throw new RuntimeException("Request already processed");
        }

        request.setStatus(Request.Status.REJECTED);
        return request;
    }

    // ✅ 6. Delete request
    public void deleteRequest(Long id) {
        if (!requestRepo.existsById(id)) {
            throw new RuntimeException("Request not found");
        }
        requestRepo.deleteById(id);
    }
}
