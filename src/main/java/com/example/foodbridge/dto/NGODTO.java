package com.example.foodbridge.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NGODTO {
    private Long id;
    private String name;
    private String address;
    private String contactNumber;
    private Integer capacity;
    private Long userId;
}
