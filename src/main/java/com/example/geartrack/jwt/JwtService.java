package com.example.geartrack.jwt;

import com.example.geartrack.models.UserModel;

public interface JwtService {

    String generateToken(UserModel userModel);

    UserModel parseToken(String jwt);

}
