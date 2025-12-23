package com.example.foodbridge.dto;

import com.example.foodbridge.model.Request;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDTO {
    private Long foodItemId;
    private Long ngoId;
    private Request.Status status;
    private LocalDateTime requestedAt;
}
