package com.example.foodbridge.service;

import com.example.foodbridge.repository.NGORepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GeoService {

    private final NGORepository ngoRepository;

    public GeoService(NGORepository ngoRepository) {
        this.ngoRepository = ngoRepository;
    }

    public List<Map<String, Object>> findNearbyNGOs(Double lat, Double lng, Double radiusKm) {
        List<Object[]> results = ngoRepository.findNearbyNGOs(lat, lng, radiusKm);

        List<Map<String, Object>> nearbyList = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> ngoData = new HashMap<>();
            ngoData.put("id", row[0]);
            ngoData.put("name", row[1]);
            ngoData.put("address", row[2]);
            ngoData.put("contactNumber", row[3]);
            ngoData.put("capacity", row[4]);
            ngoData.put("latitude", row[5]);
            ngoData.put("longitude", row[6]);
            ngoData.put("distance_km", row[7]);
            nearbyList.add(ngoData);
        }

        return nearbyList;
    }
}
