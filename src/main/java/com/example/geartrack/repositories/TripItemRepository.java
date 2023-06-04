package com.example.geartrack.repositories;

import com.example.geartrack.entities.TripEntity;
import com.example.geartrack.entities.TripItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TripItemRepository extends JpaRepository<TripItemEntity, UUID> {

    List<TripItemEntity> findByTripTripId(UUID tripId);

    @Query("""
    UPDATE TripItemEntity e
    SET e.isCollected = CASE WHEN e.isCollected = true THEN false ELSE true END
    WHERE e.trip.tripId = :tripId AND e.item.itemId = :itemId
    """)
    @Modifying
    void collectItem(@Param("tripId") UUID tripId, @Param("itemId") UUID itemId);

    Optional<TripItemEntity> findByItemNameAndTrip(String name, TripEntity trip);
}
