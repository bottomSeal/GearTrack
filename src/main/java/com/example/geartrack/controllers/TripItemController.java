package com.example.geartrack.controllers;

import com.example.geartrack.messages.requests.CollectItemRequest;
import com.example.geartrack.messages.response.FindItemResponse;
import com.example.geartrack.messages.response.ItemListResponse;
import com.example.geartrack.services.TripItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/trips_item")
@RequiredArgsConstructor
@Validated
public class TripItemController {

    private final TripItemService tripItemService;

    @PostMapping("/create/{tripId}")
    public ItemListResponse create(@PathVariable UUID tripId) {
        log.info("Create items list for trip: " + tripId.toString());

        return new ItemListResponse(tripItemService.create(tripId));
    }

    @PutMapping("/collect")
    public ItemListResponse collect(@Valid @RequestBody CollectItemRequest itemRequest) {
        log.info(itemRequest.toString());

        return new ItemListResponse(tripItemService.collect(itemRequest));
    }

    @GetMapping("/{tripId}")
    public ItemListResponse get(@PathVariable UUID tripId) {
        log.info("Get items list for trip: " + tripId.toString());

        return new ItemListResponse(tripItemService.getItems(tripId));
    }

    @GetMapping("/{tripId}/search")
    public FindItemResponse searchByName(@PathVariable UUID tripId, @RequestParam("name") @NotBlank String name) {
        log.info("Find item in trip: " + tripId + " items with name: " + name);

        return new FindItemResponse(tripItemService.findItem(tripId, name));
    }
}
