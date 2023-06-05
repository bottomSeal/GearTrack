package com.example.geartrack.messages.requests;

import com.example.geartrack.models.enums.HikingType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCreateRequest {

    @NotBlank
    private String name;

    private HikingType hikingType;

    private String description;
}
