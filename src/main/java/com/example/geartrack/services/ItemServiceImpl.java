package com.example.geartrack.services;

import com.example.geartrack.dao.ItemDao;
import com.example.geartrack.messages.requests.ItemCreateRequest;
import com.example.geartrack.models.ItemModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemDao itemDao;

    @Override
    public ItemModel create(ItemCreateRequest createRequest) {
        return ItemModel.fromEntity(itemDao.create(createRequest));
    }
}
