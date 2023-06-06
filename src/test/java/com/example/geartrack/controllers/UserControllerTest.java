package com.example.geartrack.controllers;

import com.example.geartrack.messages.requests.UserLoginRequest;
import com.example.geartrack.messages.requests.UserRegisterRequest;
import com.example.geartrack.messages.response.UserDeleteResponse;
import com.example.geartrack.messages.response.UserLoginResponse;
import com.example.geartrack.messages.response.UserRegisterResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private UserController userController;

    void loadContext(){
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "test@mail.ru", null, null)
        );
    }

    @Test
    @Order(1)
    void userRegisterIsSuccess(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest("test@mail.ru", "password");
        UserRegisterResponse userRegisterResponse = userController.register(userRegisterRequest);

        log.info("Register response: {}", userRegisterResponse);

        Assertions.assertNotNull(userRegisterResponse);

        Assertions.assertNotNull(userRegisterResponse.getToken());
    }

    @Test
    @Order(2)
    void userLoginIsSuccess(){
        UserLoginRequest userLoginRequest = new UserLoginRequest("test@mail.ru", "password");
        UserLoginResponse userLoginResponse = userController.login(userLoginRequest);

        log.info("Login response: {}", userLoginResponse);

        Assertions.assertNotNull(userLoginResponse);

        Assertions.assertNotNull(userLoginResponse.getToken());
    }

    @Test
    @Order(3)
    void userDeleteIsSuccess(){
        loadContext();
        UserDeleteResponse userDeleteResponse = userController.delete();
        log.info("Delete response: {}", userDeleteResponse);

        Assertions.assertNotNull(userDeleteResponse);

        Assertions.assertNotNull(userDeleteResponse.getEmail());
    }
}
