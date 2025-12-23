package com.example.foodbridge.repository;

import com.example.foodbridge.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {}
