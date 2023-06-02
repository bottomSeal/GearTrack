package com.example.geartrack.services;

import com.example.geartrack.messages.requests.TripCreateRequest;
import com.example.geartrack.models.TripModel;

import java.util.UUID;

public interface TripService {

    TripModel create(TripCreateRequest tripCreateRequest);

    TripModel delete(UUID tripId);

    TripModel get(UUID tripId);

}
