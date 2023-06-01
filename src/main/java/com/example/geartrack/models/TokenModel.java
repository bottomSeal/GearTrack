package com.example.geartrack.models;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TokenModel {

    private String email;

    private String token;

    private UUID userId;

}
