package com.example.foodbridge.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    private String contactNumber;
    private Long userId;
}
