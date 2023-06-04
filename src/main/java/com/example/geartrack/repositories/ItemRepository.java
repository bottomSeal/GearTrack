package com.example.geartrack.repositories;

import com.example.geartrack.entities.ItemEntity;
import com.example.geartrack.models.enums.HikingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {

    @Query("""
    SELECT i 
    FROM ItemEntity i 
    WHERE i.hikingType = 'ALL' OR i.hikingType = :hikingType
    """)
    List<ItemEntity> findByHikingType(@Param("hikingType")HikingType hikingType);
}
