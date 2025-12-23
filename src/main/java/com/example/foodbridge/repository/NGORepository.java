package com.example.foodbridge.repository;

import com.example.foodbridge.model.NGO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NGORepository extends JpaRepository<NGO, Long> {

    @Query(value = """
        SELECT *,
        (6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) *
        cos(radians(longitude) - radians(:lng)) + 
        sin(radians(:lat)) * sin(radians(latitude)))) AS distance
        FROM ngos
        HAVING distance < :radius
        ORDER BY distance ASC
        """, nativeQuery = true)
    List<Object[]> findNearbyNGOs(@Param("lat") Double latitude,@Param("lng") Double longitude,@Param("radius") Double radius);
}
