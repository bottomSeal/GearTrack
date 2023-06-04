package com.example.geartrack.messages.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectItemRequest {

    private UUID tripId;

    private UUID itemId;
}
