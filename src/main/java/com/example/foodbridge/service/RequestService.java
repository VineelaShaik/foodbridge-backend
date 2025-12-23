package com.example.foodbridge.service;

import com.example.foodbridge.model.Request;
import com.example.foodbridge.model.FoodItem;
import com.example.foodbridge.model.NGO;
import com.example.foodbridge.dto.RequestDTO;
import com.example.foodbridge.repository.RequestRepository;
import com.example.foodbridge.repository.FoodItemRepository;
import com.example.foodbridge.repository.NGORepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RequestService {

    private final RequestRepository requestRepo;
    private final FoodItemRepository foodItemRepo;
    private final NGORepository ngoRepo;

    public RequestService(RequestRepository requestRepo, FoodItemRepository foodItemRepo, NGORepository ngoRepo) {
        this.requestRepo = requestRepo;
        this.foodItemRepo = foodItemRepo;
        this.ngoRepo = ngoRepo;
    }

    // ✅ Create a new Request
    public Request createRequest(RequestDTO dto) {
        FoodItem foodItem = foodItemRepo.findById(dto.getFoodItemId())
                .orElseThrow(() -> new RuntimeException("FoodItem not found"));

        NGO ngo = ngoRepo.findById(dto.getNgoId())
                .orElseThrow(() -> new RuntimeException("NGO not found"));

        Request request = Request.builder()
                .foodItem(foodItem)
                .ngo(ngo)
                .status(dto.getStatus() != null ? dto.getStatus() : Request.Status.PENDING)
                .build();

        return requestRepo.save(request);
    }

    // ✅ Get all Requests
    public List<Request> getAllRequests() {
        return requestRepo.findAll();
    }

    // ✅ Get Request by ID
    public Request getRequestById(Long id) {
        return requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    // ✅ Update Request Status (fixed)
    public Request updateStatus(Long id, String status) {
        Request request = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        try {
            Request.Status newStatus = Request.Status.valueOf(status.toUpperCase());
            request.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + status);
        }

        return requestRepo.save(request);
    }

    // ✅ Delete a Request
    public void deleteRequest(Long id) {
        if (!requestRepo.existsById(id)) {
            throw new RuntimeException("Request not found");
        }
        requestRepo.deleteById(id);
    }
}
