package com.example.geartrack.jwt;

import com.example.geartrack.models.UserModel;
import com.example.geartrack.models.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final String ENC_KEY = "2A472D4B6150645367556B58703273357638792F423F4528482B4D6251655468";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Override
    public String generateToken(UserModel userModel) {
        return Jwts.builder()
                .setSubject(userModel.getEmail())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                .claim("userRole", userModel.getUserRole())
                .signWith(getSigningKey(), SIGNATURE_ALGORITHM)
                .compact();
    }

    @Override
    public UserModel parseToken(String jwt) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return UserModel.builder()
                .email(body.getSubject())
                .userRole(UserRole.valueOf(body.get("userRole", String.class)))
                .build();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(ENC_KEY);
        return new SecretKeySpec(keyBytes, SIGNATURE_ALGORITHM.getJcaName());
    }
}

