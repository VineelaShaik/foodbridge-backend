package com.example.foodbridge.dto;

import com.example.foodbridge.model.NGO;
import com.example.foodbridge.model.Restaurant;
import com.example.foodbridge.model.User.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
      // Restaurant-specific
    private Restaurant restaurant;
    private NGO ngo;
}
