package com.example.geartrack.services;

import com.example.geartrack.messages.requests.ItemCreateRequest;
import com.example.geartrack.models.ItemModel;

public interface ItemService {

    ItemModel create(ItemCreateRequest createRequest);
}
