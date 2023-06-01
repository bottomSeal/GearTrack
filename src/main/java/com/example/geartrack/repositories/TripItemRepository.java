package com.example.geartrack.repositories;

import com.example.geartrack.entities.TripItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripItemRepository extends JpaRepository<TripItemEntity, UUID> {
}
