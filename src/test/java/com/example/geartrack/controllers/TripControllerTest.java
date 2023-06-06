package com.example.geartrack.controllers;

import com.example.geartrack.messages.requests.TripCreateRequest;
import com.example.geartrack.messages.response.*;
import com.example.geartrack.models.TripModel;
import com.example.geartrack.models.enums.HikingType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TripControllerTest {

    @Autowired
    private TripController tripController;

    private static UUID tripId;

    @BeforeEach
    void loadContext(){
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "asdf@mail.ru", null, null)
                // Устанавливаю в контекст заранее зарегистрированного пользователя
        );
    }

    @Test
    @Order(1)
    void tripCreateIsSuccess(){

        TripCreateRequest tripCreateRequest = new TripCreateRequest("Алтай", HikingType.MOUNTAIN,
                null, LocalDate.now(), LocalDate.parse("2023-02-21"));

        TripCreateResponse tripCreateResponse = tripController.create(tripCreateRequest);

        tripId = tripCreateResponse.getTripId();

        System.out.println(tripId);

        Assertions.assertNotNull(tripCreateResponse);

        Assertions.assertEquals(tripCreateRequest.getName(), tripCreateResponse.getName());
    }

    @Test
    @Order(2)
    void getAllTripsIsSuccess(){

        TripGetAllResponse tripGetAllResponse = tripController.getAll();

        Assertions.assertNotNull(tripGetAllResponse);
    }

    @Test
    @Order(3)
    void getTripIsSuccess() {

        System.out.println(tripId);

        TripGetResponse tripGetResponse = tripController.get(tripId);

        Assertions.assertNotNull(tripGetResponse);

        Assertions.assertEquals(tripGetResponse.getName(), "Алтай");
    }

    @Test
    @Order(4)
    void deleteTripIsSuccess() {

        System.out.println(tripId);

        TripDeleteResponse tripDeleteResponse = tripController.delete(tripId);

        Assertions.assertNotNull(tripDeleteResponse);

        Assertions.assertEquals(tripDeleteResponse.getName(), "Алтай");
    }
}
