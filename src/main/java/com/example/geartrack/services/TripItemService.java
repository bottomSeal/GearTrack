package com.example.geartrack.services;

import com.example.geartrack.messages.requests.CollectItemRequest;
import com.example.geartrack.models.ItemModel;

import java.util.List;
import java.util.UUID;

public interface TripItemService {

    List<ItemModel> create(UUID tripId);

    List<ItemModel> collect(CollectItemRequest itemRequest);

    List<ItemModel> getItems(UUID tripId);

    ItemModel findItem(UUID tripId, String name);
}
