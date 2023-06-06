package com.example.geartrack.controllers;

import com.example.geartrack.dao.TripDao;
import com.example.geartrack.messages.requests.CollectItemRequest;
import com.example.geartrack.messages.requests.TripCreateRequest;
import com.example.geartrack.messages.response.FindItemResponse;
import com.example.geartrack.messages.response.ItemListResponse;
import com.example.geartrack.messages.response.TripCreateResponse;
import com.example.geartrack.models.ItemModel;
import com.example.geartrack.models.TripModel;
import com.example.geartrack.models.enums.HikingType;
import com.example.geartrack.services.TripService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TripItemControllerTest {

    @Autowired
    private TripItemController tripItemController;

    private static UUID tripId;

    @Autowired
    private TripService tripService;

    @BeforeEach
    void loadContext(){
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "asdf@mail.ru", null, null)
        );
    }

    @Test
    @Order(1)
    void gearCreateIsSuccess(){

        TripCreateRequest tripCreateRequest = new TripCreateRequest("Алтай", HikingType.MOUNTAIN,
                null, LocalDate.now(), LocalDate.parse("2023-02-21"));

        TripModel tripModel = tripService.create(tripCreateRequest); // Создаю поход

        tripId = tripModel.getTripId(); // Беру значения id для дальнейших тестов

        ItemListResponse itemListResponse = tripItemController.create(tripId);

        Assertions.assertNotNull(itemListResponse);
    }

    @Test
    @Order(2)
    void gearGetIsSuccess(){

        ItemListResponse itemListResponse = tripItemController.get(tripId);

        Assertions.assertNotNull(itemListResponse);
    }

    @Test
    @Order(3)
    void collectItemIsSuccess(){

        UUID ITEM_ID = UUID.fromString("3c7343e6-ecd1-489d-ba86-a42100abee7a");
        // В БД лежит предмет КЛМН с таким id и типом похода ALL, поэтому он добавляется в каждый поход

        CollectItemRequest collectItemRequest = new CollectItemRequest(tripId, ITEM_ID);

        ItemListResponse itemListResponse = tripItemController.collect(collectItemRequest);

        Assertions.assertNotNull(itemListResponse);

        for (ItemModel item: itemListResponse.getItems()) {
            if (!Objects.equals(item.getName(), "КЛМН")){
                break;
            }

            Assertions.assertTrue(item.isCollected());
        }
    }

    @Test
    @Order(4)
    void searchItemIsSuccess(){

        FindItemResponse findItemResponse = tripItemController.searchByName(tripId, "КЛМН");

        Assertions.assertNotNull(findItemResponse);

        Assertions.assertEquals(findItemResponse.getItem().getName(), "КЛМН");
    }
}
