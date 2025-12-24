package com.example.foodbridge.controller;

import com.example.foodbridge.dto.RequestDTO;
import com.example.foodbridge.model.Donation;
import com.example.foodbridge.model.Request;
import com.example.foodbridge.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    // ✅ 1. NGO creates a new request
    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody RequestDTO dto) {
        Request request = requestService.createRequest(dto);
        return ResponseEntity.ok(request);
    }

    // ✅ 2. Get all requests
    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    // ✅ 3. Get request by ID
    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    // ✅ 4. Approve request → Donation is created
    @PutMapping("/{id}/approve")
    public ResponseEntity<Donation> approveRequest(
            @PathVariable Long id,
            @RequestParam int quantityDonated) {

        Donation donation = requestService.approveRequest(id, quantityDonated);
        return ResponseEntity.ok(donation);
    }

    // ✅ 5. Reject request
    @PutMapping("/{id}/reject")
    public ResponseEntity<Request> rejectRequest(@PathVariable Long id) {
        Request rejected = requestService.rejectRequest(id);
        return ResponseEntity.ok(rejected);
    }

    // ✅ 6. Delete request (optional)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.ok("Request deleted successfully");
    }
}
