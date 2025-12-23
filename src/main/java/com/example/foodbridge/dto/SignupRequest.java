package com.example.foodbridge.dto;

import com.example.foodbridge.model.User;
import lombok.*;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private User.Role role;  // ADMIN, RESTAURANT, NGO
}
