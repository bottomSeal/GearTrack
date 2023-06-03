package com.example.geartrack.messages.response;

import com.example.geartrack.entities.ItemEntity;
import com.example.geartrack.models.enums.HikingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripCreateResponse {

    private String name;

    private HikingType hikingType;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private UUID tripId;

    private Set<ItemEntity> items;
}
