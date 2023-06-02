package com.example.geartrack.dao;

import com.example.geartrack.entities.UserEntity;
import com.example.geartrack.models.enums.UserRole;
import com.example.geartrack.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    public boolean isUserExist(UUID userId) {
        return userRepository.findById(userId).isPresent();
    }

    public boolean isUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserEntity getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Transactional
    public UserEntity createUser(String email, String password, UserRole role){

        return userRepository.save(UserEntity.builder()
                .email(email)
                .password(password)
                .registerDate(LocalDate.now())
                .userRole(role)
                .build());
    }

    public String getPasswordHash(UUID userId){
        return userRepository.findById(userId).orElseThrow().getPassword();
    }

    public String getPasswordHash(String email){
        return userRepository.findByEmail(email).orElseThrow().getPassword();
    }

    @Transactional
    public void deleteById(UUID userId){
        userRepository.deleteById(userId);
    }
}
