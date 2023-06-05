package com.example.geartrack.controllers;

import com.example.geartrack.messages.requests.ItemCreateRequest;
import com.example.geartrack.messages.response.ItemCreateResponse;
import com.example.geartrack.models.ItemModel;
import com.example.geartrack.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    public ItemCreateResponse create(@Valid @RequestBody ItemCreateRequest createRequest) {
        log.info(createRequest.toString());

        ItemModel itemModel = itemService.create(createRequest);

        return new ItemCreateResponse(itemModel.getName(), itemModel.getDescription());
    }
}
