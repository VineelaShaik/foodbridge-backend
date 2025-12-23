package com.example.foodbridge.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.foodbridge.model.NGO;
import com.example.foodbridge.model.Restaurant;
import com.example.foodbridge.model.User;
import com.example.foodbridge.repository.NGORepository;
import com.example.foodbridge.repository.RestaurantRepository;
import com.example.foodbridge.repository.UserRepository;

import jakarta.transaction.Transactional;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final NGORepository ngoRepository;

    public UserService(UserRepository userRepository, RestaurantRepository restaurantRepository, NGORepository ngoRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.ngoRepository = ngoRepository;
    }
@Transactional
public User createUser(User user2) {
    User user = User.builder()
            .name(user2.getName())
            .email(user2.getEmail())
            .password(user2.getPassword())
            .role(user2.getRole())
            .build();

    if (user2.getRole() == User.Role.RESTAURANT && user2.getRestaurant() != null) {
        Restaurant restaurant = Restaurant.builder()
                .name(user2.getRestaurant().getName())
                .address(user2.getRestaurant().getAddress())
                .contactNumber(user2.getRestaurant().getContactNumber())
                .user(user)   // link child -> parent
                .latitude(12.90 + Math.random() * 0.2)
                .longitude(80.10 + Math.random() * 0.2)
                .build();
        user.setRestaurant(restaurant); // link parent -> child
    } 
    else if (user2.getRole() == User.Role.NGO && user2.getNgo() != null) {
        NGO ngo = NGO.builder()
                .name(user2.getNgo().getName())
                .address(user2.getNgo().getAddress())
                .contactNumber(user2.getNgo().getContactNumber())
                .capacity(user2.getNgo().getCapacity())
                .user(user)   // link child -> parent
                .latitude(12.90 + Math.random() * 0.2)
                .longitude(80.10 + Math.random() * 0.2)
                .build();
        user.setNgo(ngo); // link parent -> child
    }

    return userRepository.save(user); // this will save User + NGO/Restaurant
}


public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Update user
    public User updateUser(Long id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        user.setPassword(updatedUser.getPassword());
        // user.setCapacity(updatedUser.getNgo().getCapacity());
        // Update other fields as needed
        

        return userRepository.save(user);
    }

    // Delete user
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
