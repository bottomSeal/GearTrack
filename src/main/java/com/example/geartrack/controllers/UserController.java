package com.example.geartrack.controllers;

import com.example.geartrack.messages.requests.UserLoginRequest;
import com.example.geartrack.messages.requests.UserRegisterRequest;
import com.example.geartrack.messages.response.UserDeleteResponse;
import com.example.geartrack.messages.response.UserLoginResponse;
import com.example.geartrack.messages.response.UserRegisterResponse;
import com.example.geartrack.models.TokenModel;
import com.example.geartrack.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserRegisterResponse register(@Valid @RequestBody UserRegisterRequest registerRequest){
        log.info(registerRequest.toString());

        TokenModel tokenModel = userService.register(registerRequest);

        return new UserRegisterResponse(
                tokenModel.getEmail(),
                tokenModel.getUserId(),
                tokenModel.getToken()
        );
    }

    @PostMapping("/login")
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest loginRequest){
        log.info(loginRequest.toString());

        TokenModel tokenModel = userService.login(loginRequest);

        return new UserLoginResponse(
                tokenModel.getEmail(),
                tokenModel.getToken(),
                tokenModel.getUserId()
        );
    }

    @DeleteMapping("/delete")
    public UserDeleteResponse delete(){

        TokenModel tokenModel = userService.delete();

        return new UserDeleteResponse(tokenModel.getEmail());
    }
}
