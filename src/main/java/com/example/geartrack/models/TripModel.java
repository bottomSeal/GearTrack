package com.example.geartrack.models;

import com.example.geartrack.entities.TripEntity;
import com.example.geartrack.models.enums.HikingType;
import lombok.*;

import java.time.LocalDate;

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

    public static TripModel fromEntity(TripEntity tripEntity) {
        return TripModel.builder()
                .name(tripEntity.getName())
                .description(tripEntity.getDescription())
                .hikingType(tripEntity.getHikingType())
                .startDate(tripEntity.getStartDate())
                .endDate(tripEntity.getEndDate())
                .build();
    }
}
