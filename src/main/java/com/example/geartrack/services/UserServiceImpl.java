package com.example.geartrack.services;

import com.example.geartrack.dao.UserDao;
import com.example.geartrack.entities.UserEntity;
import com.example.geartrack.jwt.JwtService;
import com.example.geartrack.messages.requests.UserLoginRequest;
import com.example.geartrack.messages.requests.UserRegisterRequest;
import com.example.geartrack.models.TokenModel;
import com.example.geartrack.models.UserModel;
import com.example.geartrack.models.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public TokenModel register(UserRegisterRequest registerRequest) {

        if(userDao.isUserExist(registerRequest.getEmail())) {
            log.info("User with same email already exist");
            throw new IllegalArgumentException("User with same email already exist");
        }

        UserEntity newUser = userDao.createUser(
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                UserRole.USER
        );


        String jwt = jwtService.generateToken(UserModel.fromEntity(newUser));

        return new TokenModel(
                newUser.getEmail(),
                jwt,
                newUser.getUserId()
        );
    }

    @Override
    public TokenModel login(UserLoginRequest loginRequest) {

        if(!userDao.isUserExist(loginRequest.getEmail())){
            log.info("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), userDao.getPasswordHash(loginRequest.getEmail()))){
            throw new UsernameNotFoundException("Password not match");
        }

        UserModel user = UserModel.fromEntity(userDao.getUserByEmail(loginRequest.getEmail()));

        return new TokenModel(user.getEmail(), jwtService.generateToken(user), user.getUserId());
    }

    @Override
    public TokenModel delete() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = userDao.getUserByEmail(authentication.getName());

        userDao.deleteById(user.getUserId());

        return TokenModel.builder().email(user.getEmail()).build();
    }
}
