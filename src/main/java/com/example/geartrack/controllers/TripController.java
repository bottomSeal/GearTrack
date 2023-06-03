package com.example.geartrack.controllers;

import com.example.geartrack.entities.TripEntity;
import com.example.geartrack.messages.requests.TripCreateRequest;
import com.example.geartrack.messages.response.TripCreateResponse;
import com.example.geartrack.messages.response.TripDeleteResponse;
import com.example.geartrack.messages.response.TripGetAllResponse;
import com.example.geartrack.messages.response.TripGetResponse;
import com.example.geartrack.models.TripModel;
import com.example.geartrack.services.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping("/create")
    public TripCreateResponse create(@RequestBody TripCreateRequest createRequest){
        log.info(createRequest.toString());

        TripModel tripModel = tripService.create(createRequest);

        return TripCreateResponse.builder()
                .name(tripModel.getName())
                .description(tripModel.getDescription())
                .startDate(tripModel.getStartDate())
                .endDate(tripModel.getEndDate())
                .hikingType(tripModel.getHikingType())
                .tripId(tripModel.getTripId())
                .items(tripModel.getItems())
                .build();
    }


    @GetMapping("/{tripId}")
    public TripGetResponse get(@PathVariable UUID tripId){
        log.info("Get trip: " + tripId.toString());

        TripModel tripModel = tripService.get(tripId);

        return TripGetResponse.builder()
                .tripId(tripModel.getTripId())
                .name(tripModel.getName())
                .description(tripModel.getDescription())
                .startDate(tripModel.getStartDate())
                .endDate(tripModel.getEndDate())
                .hikingType(tripModel.getHikingType())
                .items(tripModel.getItems())
                .build();
    }


    @GetMapping()
    public TripGetAllResponse getAll() {
        log.info("Get all trips");

        return new TripGetAllResponse(tripService.getAll());
    }


    @DeleteMapping("/delete/{tripId}")
    public TripDeleteResponse delete(@PathVariable UUID tripId){
        log.info("Delete trip: " + tripId.toString());

        TripModel tripModel = tripService.delete(tripId);

        return new TripDeleteResponse(tripModel.getName());
    }
}
