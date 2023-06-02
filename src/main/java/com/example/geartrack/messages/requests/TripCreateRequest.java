package com.example.geartrack.messages.requests;

import com.example.geartrack.models.enums.HikingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripCreateRequest {

    private String name;

    private HikingType hikingType;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
