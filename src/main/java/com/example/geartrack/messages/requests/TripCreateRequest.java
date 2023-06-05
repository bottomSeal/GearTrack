package com.example.geartrack.messages.requests;

import com.example.geartrack.models.enums.HikingType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripCreateRequest {

    @NotBlank
    private String name;

    private HikingType hikingType;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
