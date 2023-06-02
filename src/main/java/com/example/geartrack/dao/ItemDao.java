package com.example.geartrack.dao;

import com.example.geartrack.entities.ItemEntity;
import com.example.geartrack.messages.requests.ItemCreateRequest;
import com.example.geartrack.repositories.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemDao {

    private final ItemRepository itemRepository;

    @Transactional
    public ItemEntity create(ItemCreateRequest request) {

        return itemRepository.save(ItemEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .hikingType(request.getHikingType())
                .build());
    }
}
