package com.example.geartrack.jwt;

import com.example.geartrack.models.UserModel;
import com.example.geartrack.models.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final String ENC_KEY = "2A472D4B6150645367556B58703273357638792F423F4528482B4D6251655468";

    @Override
    public String generateToken(UserModel userModel) {
        return Jwts.builder()
                .signWith(getSignInKey())
                .setSubject(userModel.getEmail())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(600)))
                .claim("userRole", userModel.getUserRole())
                .compact();
    }

    @Override
    public UserModel parseToken(String jwt) {
        Jwt token = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parse(jwt);

        Claims body = (Claims) token.getBody();

        return UserModel.builder()
                .email(body.getSubject())
                .userRole(UserRole.valueOf(body.get("userRole", String.class)))
                .build();
    }


    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(ENC_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
