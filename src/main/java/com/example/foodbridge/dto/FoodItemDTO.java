package com.example.foodbridge.dto;

import com.example.foodbridge.model.FoodItem;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItemDTO {
    private String name;
    private Integer quantity;
    private LocalDate expiryDate;
    private FoodItem.Status status;
    private Long restaurantId;
}
