package com.example.geartrack.controllers;

import com.example.geartrack.dao.UserDao;
import com.example.geartrack.messages.requests.ItemCreateRequest;
import com.example.geartrack.messages.response.ItemCreateResponse;
import com.example.geartrack.models.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.example.geartrack.models.enums.HikingType.ALL;

@SpringBootTest
@Slf4j
class ItemControllerTest {

    @Autowired
    private ItemController itemController;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    void loadContext(){
        UserModel admin = UserModel.fromEntity(userDao.getUserByEmail("admin@gmail.com"));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        admin.getEmail(), null, admin.getAuthorities())
        );
    }

    @Test
    void addItemIsSuccess() {

        ItemCreateRequest itemCreateRequest = new ItemCreateRequest("Палатка", ALL, null);

        ItemCreateResponse itemCreateResponse = itemController.create(itemCreateRequest);

        Assertions.assertNotNull(itemCreateResponse);

        Assertions.assertEquals(itemCreateRequest.getName(), itemCreateResponse.getName());
    }

}
