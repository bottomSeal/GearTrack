package com.example.geartrack.services;

import com.example.geartrack.dao.TripDao;
import com.example.geartrack.dao.UserDao;
import com.example.geartrack.entities.UserEntity;
import com.example.geartrack.messages.requests.TripCreateRequest;
import com.example.geartrack.models.TripModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripServiceImpl implements TripService{

    private final UserDao userDao;

    private final TripDao tripDao;

    private UserEntity getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userDao.getUserByEmail(authentication.getName());
    }

    @Override
    public TripModel create(TripCreateRequest tripCreateRequest) {

        return TripModel.fromEntity(tripDao.create(tripCreateRequest, getUserFromContext()));
    }

    @Override
    public TripModel get(UUID tripId) {

        return TripModel.fromEntity(tripDao.get(tripId, getUserFromContext()));
    }


    @Override
    public TripModel delete(UUID tripId) {

        return TripModel.fromEntity(tripDao.delete(tripId, getUserFromContext()));
    }
}
