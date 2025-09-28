package org.semicolon.semicolonartworksystem.controllers;

import lombok.extern.slf4j.Slf4j;
import org.semicolon.semicolonartworksystem.data.models.User;
import org.semicolon.semicolonartworksystem.dtos.requests.LoginRequest;
import org.semicolon.semicolonartworksystem.dtos.requests.SignUpRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.LoginResponse;
import org.semicolon.semicolonartworksystem.dtos.responses.SignUpResponse;
import org.semicolon.semicolonartworksystem.exceptions.InvalidEmailException;
import org.semicolon.semicolonartworksystem.services.UserServicesImpl;
import org.semicolon.semicolonartworksystem.utils.JwtUtil;
import org.semicolon.semicolonartworksystem.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserServicesImpl userServicesImpl;

    @PostMapping("/find-login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        System.out.println("111111111111");
        if(!Validator.isValidEmail(request.getEmail())) {
            throw new InvalidEmailException("Invalid email format");
        }
        System.out.println("222222222");

        LoginResponse response = userServicesImpl.login(request);
        System.out.println("3333333333");
        String token = JwtUtil.generateToken(request.getEmail());
        System.out.println("44444444444");
        response.setToken(token);
        System.out.println("5555555555555 ");
        return response;
    }

    @PostMapping("/enter-signup")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        if(!Validator.isValidEmail(request.getEmail())) {
            throw new InvalidEmailException("Invalid email format");
        }
        return userServicesImpl.signUp(request);
    }
}


