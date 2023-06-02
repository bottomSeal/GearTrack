package com.example.geartrack.repositories;

import com.example.geartrack.entities.TripEntity;
import com.example.geartrack.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TripRepository extends JpaRepository<TripEntity, UUID> {

    Optional<TripEntity> findByTripIdAndTripOwner(UUID tripId, UserEntity owner) ;
}
