package com.example.geartrack.models;

import com.example.geartrack.entities.ItemEntity;
import com.example.geartrack.entities.TripEntity;
import com.example.geartrack.models.enums.HikingType;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TripModel {

    private String name;

    private HikingType hikingType;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private UUID tripId;

    public static TripModel fromEntity(TripEntity tripEntity) {
        return TripModel.builder()
                .name(tripEntity.getName())
                .description(tripEntity.getDescription())
                .hikingType(tripEntity.getHikingType())
                .startDate(tripEntity.getStartDate())
                .endDate(tripEntity.getEndDate())
                .tripId(tripEntity.getTripId())
                .build();
    }
}
