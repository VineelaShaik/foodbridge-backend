package com.example.foodbridge.service;

import com.example.foodbridge.model.NGO;
import com.example.foodbridge.model.User;
import com.example.foodbridge.dto.NGODTO;
import com.example.foodbridge.repository.NGORepository;
import com.example.foodbridge.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class NGOService {

    private final NGORepository ngoRepo;
    private final UserRepository userRepo;

    public NGOService(NGORepository ngoRepo, UserRepository userRepo) {
        this.ngoRepo = ngoRepo;
        this.userRepo = userRepo;
    }

    public NGO createNGO(NGODTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        NGO ngo = NGO.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .contactNumber(dto.getContactNumber())
                .capacity(dto.getCapacity())
                .latitude(12.90 + Math.random() * 0.2)
                .longitude(80.10 + Math.random() * 0.2)
                .user(user)
                .build();

        return ngoRepo.save(ngo);
    }

    public List<NGO> getAllNGOs() {
        return ngoRepo.findAll();
    }
    public NGO getNGOById(Long id) {
    return ngoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("NGO not found with id: " + id));
}
    @PutMapping("{id}")
    public NGO updateNGO(@PathVariable Long id, @RequestBody NGO updatedNgo) {
        NGO ngo = ngoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("NGO not found with id: " + id));

        ngo.setName(updatedNgo.getName());
        ngo.setAddress(updatedNgo.getAddress());
        ngo.setContactNumber(updatedNgo.getContactNumber());
        ngo.setCapacity(updatedNgo.getCapacity());
        
        // If NGO has user mapping
        if (updatedNgo.getUser() != null) {
            ngo.setUser(updatedNgo.getUser());
        }

        return ngoRepo.save(ngo);
    }

}
