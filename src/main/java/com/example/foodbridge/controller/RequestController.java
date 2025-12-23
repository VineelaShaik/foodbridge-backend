package com.example.foodbridge.controller;

import com.example.foodbridge.dto.RequestDTO;
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

    // ✅ Create a new request
    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody RequestDTO dto) {
        Request request = requestService.createRequest(dto);
        return ResponseEntity.ok(request);
    }

    // ✅ Get all requests
    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = requestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    // ✅ Get request by ID (optional but useful)
    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        Request request = requestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    // ✅ Update status (for example: pending → approved → completed)
    @PutMapping("/{id}")
    public ResponseEntity<Request> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Request updatedRequest = requestService.updateStatus(id, status);
        return ResponseEntity.ok(updatedRequest);
    }

    // ✅ Delete request (optional)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.ok("Request deleted successfully");
    }
}
