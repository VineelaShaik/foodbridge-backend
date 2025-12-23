package com.example.foodbridge.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_item_id", nullable = false)
    private FoodItem foodItem;

    @ManyToOne
    @JoinColumn(name = "ngo_id", nullable = false)
    private NGO ngo;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING; // Default status

    @CreationTimestamp
    private LocalDateTime requestedAt; // Automatically handled by Hibernate

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED,
        COMPLETED
    }
}
