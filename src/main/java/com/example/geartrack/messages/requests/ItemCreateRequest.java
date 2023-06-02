package com.example.geartrack.messages.requests;

import com.example.geartrack.models.enums.HikingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCreateRequest {

    private String name;

    private HikingType hikingType;

    private String description;
}
