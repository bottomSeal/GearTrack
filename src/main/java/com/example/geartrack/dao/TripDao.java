package com.example.geartrack.dao;

import com.example.geartrack.entities.ItemEntity;
import com.example.geartrack.entities.TripEntity;
import com.example.geartrack.entities.TripItemEntity;
import com.example.geartrack.entities.UserEntity;
import com.example.geartrack.messages.requests.TripCreateRequest;
import com.example.geartrack.repositories.ItemRepository;
import com.example.geartrack.repositories.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class TripDao {

    private final TripRepository tripRepository;

    private final ItemRepository itemRepository;

    @Transactional
    public TripEntity create(TripCreateRequest request, UserEntity tripOwner) {

        return tripRepository.save(TripEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .hikingType(request.getHikingType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .tripOwner(tripOwner)
                .build());
    }

    @Transactional
    public TripEntity createTripWithItems(TripEntity tripEntity) {
        TripEntity savedTrip = tripRepository.save(tripEntity);

        List<ItemEntity> items = itemRepository.findByHikingType(tripEntity.getHikingType());

        Set<TripItemEntity> tripItems = new HashSet<>();
        for (ItemEntity item : items) {
            TripItemEntity tripItem = new TripItemEntity();
            tripItem.setTrip(savedTrip);
            tripItem.setItem(item);
            tripItems.add(tripItem);
        }

        savedTrip.setTripItems(tripItems);

        return tripRepository.save(savedTrip);
    }

    public TripEntity get(UUID tripId, UserEntity tripOwner) {

        return tripRepository.findByTripIdAndTripOwner(tripId, tripOwner).orElseThrow();
    }

    public List<TripEntity> getAll(UserEntity tripOwner) {

        return tripRepository.findAllByTripOwner(tripOwner);
    }

    @Transactional
    public TripEntity delete(UUID tripId, UserEntity tripOwner) {

        TripEntity trip = tripRepository.findByTripIdAndTripOwner(tripId, tripOwner).orElseThrow();

        tripRepository.delete(trip);

        return trip;
    }
}
