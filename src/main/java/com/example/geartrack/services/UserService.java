package com.example.geartrack.services;

import com.example.geartrack.messages.requests.UserLoginRequest;
import com.example.geartrack.messages.requests.UserRegisterRequest;
import com.example.geartrack.models.TokenModel;

public interface UserService {

    TokenModel register(UserRegisterRequest registerRequest);

    TokenModel login(UserLoginRequest loginRequest);

    TokenModel delete();

}
