package com.example.geartrack.repositories;

import com.example.geartrack.entities.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<TripEntity, UUID> {
}
