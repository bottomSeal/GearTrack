package com.example.geartrack.services;

import com.example.geartrack.dao.TripItemDao;
import com.example.geartrack.dao.UserDao;
import com.example.geartrack.entities.ItemEntity;
import com.example.geartrack.entities.UserEntity;
import com.example.geartrack.messages.requests.CollectItemRequest;
import com.example.geartrack.models.ItemModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripItemServiceImpl implements TripItemService{

    private final TripItemDao tripItemDao;

    private final UserDao userDao;

    private UserEntity getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userDao.getUserByEmail(authentication.getName());
    }

    private List<ItemModel> fromEntityArrayToModelArray(List<ItemEntity> items) {

        List<ItemModel> itemsModel= new ArrayList<>();

        for (ItemEntity item: items) {
            ItemModel itemModel = ItemModel.fromEntity(item);
            itemModel.setCollected(item.isCollected());

            itemsModel.add(itemModel);
        }

        return itemsModel;
    }

    @Override
    public List<ItemModel> create(UUID tripId) {

        return this.fromEntityArrayToModelArray(
                tripItemDao.createItemListForTrip(tripId, getUserFromContext())
        );
    }

    @Override
    public void collect(CollectItemRequest itemRequest) {

        tripItemDao.collectItem(itemRequest.getTripId(), itemRequest.getItemId(), getUserFromContext());
    }

    @Override
    public List<ItemModel> getItems(UUID tripId) {

        return this.fromEntityArrayToModelArray(
                tripItemDao.getItemsForTrip(tripId, getUserFromContext())
        );
    }

    @Override
    public ItemModel findItem(UUID tripId, String name) {

        ItemEntity entity = tripItemDao.findByName(tripId, name, getUserFromContext());

        ItemModel item = ItemModel.fromEntity(entity);

        item.setCollected(entity.isCollected());

        return item;
    }
}
