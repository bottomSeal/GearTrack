package com.example.geartrack.messages.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {

    private String email;

    private String token;

    private UUID uuid;
}
