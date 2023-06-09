package com.example.geartrack.dao;

import com.example.geartrack.entities.ItemEntity;
import com.example.geartrack.entities.TripEntity;
import com.example.geartrack.entities.TripItemEntity;
import com.example.geartrack.entities.UserEntity;
import com.example.geartrack.repositories.ItemRepository;
import com.example.geartrack.repositories.TripItemRepository;
import com.example.geartrack.repositories.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TripItemDao {

    private final TripRepository tripRepository;

    private final ItemRepository itemRepository;

    private final TripItemRepository tripItemRepository;

    public List<ItemEntity> getItemsForTrip(UUID tripId, UserEntity user) {

        TripEntity trip = tripRepository.findByTripIdAndTripOwner(tripId, user).orElseThrow();

        List<TripItemEntity> tripItems = tripItemRepository.findByTripTripId(trip.getTripId());
        List<ItemEntity> items = new ArrayList<>();

        for (TripItemEntity tripItem : tripItems) {
            ItemEntity item = tripItem.getItem();
            item.setCollected(tripItem.isCollected());
            items.add(item);
        }

        return items;
    }

    @Transactional
    public List<ItemEntity> createItemListForTrip(UUID tripId, UserEntity user) {

        TripEntity savedTrip = tripRepository.findByTripIdAndTripOwner(tripId, user).orElseThrow();

        List<ItemEntity> items = itemRepository.findByHikingType(savedTrip.getHikingType());

        Set<TripItemEntity> tripItems = new HashSet<>();
        for (ItemEntity item : items) {
            TripItemEntity tripItem = tripItemRepository.save(
                    TripItemEntity.builder()
                            .trip(savedTrip)
                            .item(item)
                            .isCollected(false)
                            .build());

            tripItems.add(tripItem);
        }

        savedTrip.getTripItems().removeIf(tripItem -> !tripItems.contains(tripItem));

        TripEntity trip = tripRepository.save(savedTrip);

        return this.getItemsForTrip(trip.getTripId(), user);
    }


    @Transactional
    public void collectItem(UUID tripId, UUID itemId, UserEntity user) {

        TripEntity trip = tripRepository.findByTripIdAndTripOwner(tripId, user).orElseThrow();

        tripItemRepository.collectItem(trip.getTripId(), itemId);
    }

    public ItemEntity findByName(UUID tripId, String name, UserEntity user) {

        TripEntity trip = tripRepository.findByTripIdAndTripOwner(tripId, user).orElseThrow();

        TripItemEntity tripItem = tripItemRepository.findByItemNameAndTrip(name, trip).orElseThrow();

        ItemEntity item = tripItem.getItem();
        item.setCollected(tripItem.isCollected());

        return item;
    }
}
