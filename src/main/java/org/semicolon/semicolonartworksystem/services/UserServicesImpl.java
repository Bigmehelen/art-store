package org.semicolon.semicolonartworksystem.services;

import org.semicolon.semicolonartworksystem.data.models.Role;
import org.semicolon.semicolonartworksystem.data.models.User;
import org.semicolon.semicolonartworksystem.data.models.UserPrincipal;
import org.semicolon.semicolonartworksystem.data.repositories.UserRepo;
import org.semicolon.semicolonartworksystem.dtos.requests.LoginRequest;
import org.semicolon.semicolonartworksystem.dtos.requests.SignUpRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.LoginResponse;
import org.semicolon.semicolonartworksystem.dtos.responses.SignUpResponse;
import org.semicolon.semicolonartworksystem.exceptions.UserAlreadyExistsException;
import org.semicolon.semicolonartworksystem.exceptions.UserNotFoundException;
import org.semicolon.semicolonartworksystem.utils.JwtUtil;
import org.semicolon.semicolonartworksystem.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = Mapper.map(request);
        User saved = userRepo.findByEmail(user.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        UserPrincipal  userPrincipal = new UserPrincipal(saved);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtUtil.generateToken(userPrincipal));
        return loginResponse;
    }

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        if(userRepo.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("User with email" + request.getEmail() + "already exists");
        }
        User user = Mapper.mapToModel(request);
        user.setRole(Set.of(Role.BUYER));
        User saved = userRepo.save(user);
        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setToken(jwtUtil.generateToken(new UserPrincipal(saved)));
        return signUpResponse;
    }

    @Override
    public Long inventory() {
        return userRepo.count();
    }

    @Override
    public SignUpResponse signUpAsAdmin(SignUpRequest request) {
        User user = Mapper.mapToModel(request);
        user.setRole(Set.of(Role.ADMIN));
        String token = jwtUtil.generateToken(new UserPrincipal(user));
        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setToken(token);
        return signUpResponse;
    }

}
