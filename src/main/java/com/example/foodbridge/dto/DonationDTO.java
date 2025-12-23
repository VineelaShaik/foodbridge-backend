package com.example.foodbridge.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationDTO {
    private Long foodItemId;
    private Long ngoId;
    private Long restaurantId;
    private Integer quantityDonated;
    private LocalDateTime donatedAt;
}
