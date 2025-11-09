package org.semicolon.semicolonartworksystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.semicolon.semicolonartworksystem.data.models.Role;
import org.semicolon.semicolonartworksystem.data.models.User;
import org.semicolon.semicolonartworksystem.data.repositories.UserRepo;
import org.semicolon.semicolonartworksystem.dtos.requests.LoginRequest;
import org.semicolon.semicolonartworksystem.dtos.requests.SignUpRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.LoginResponse;
import org.semicolon.semicolonartworksystem.dtos.responses.SignUpResponse;
import org.semicolon.semicolonartworksystem.utils.JwtUtil;
import org.semicolon.semicolonartworksystem.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.semicolon.semicolonartworksystem.data.models.Role.BUYER;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServicesImplTest {
    LoginRequest request;
    LoginResponse response;
    SignUpRequest signUpRequest;
    SignUpResponse signUpResponse;

    @MockitoBean
    private UserRepo userRepo;

    @Autowired
    private UserServices userService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        request = new LoginRequest();
        request.setEmail("JayOne@gmail.com");
        request.setPassword("password123");

        signUpRequest = new SignUpRequest();
        signUpRequest.setUserName("JayOne");
        signUpRequest.setEmail("jayOne@gmail.com");
        signUpRequest.setPassword("password123");

        response = new LoginResponse();
        signUpResponse = new SignUpResponse();
    }

    @Test
    public void testThatUserCanLoginWithCorrectPassword() {
        User user = Mapper.map(request);
        when(userRepo.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any())).thenReturn("token");
        LoginResponse loginResponse = userService.login(request);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getToken()).isNotNull();
    }

    @Test
    public void testThatUserCanSignUpWithCorrectFields() {
        User user = Mapper.mapToModel(signUpRequest);
        when(userRepo.save(any())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn("token");
        SignUpResponse signUpResponse = userService.signUp(signUpRequest);
        assertThat(signUpResponse).isNotNull();
        assertThat(signUpResponse.getToken()).isNotNull();
        verify(userRepo, times(1)).save(any());
    }

    @Test
    void testThatCanSignUpAsAdminWithCorrectDetails(){
        User user = Mapper.mapToModel(signUpRequest);
        when(userRepo.save(any())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn("token");
        SignUpResponse signUpResponse = userService.signUpAsAdmin(signUpRequest);
        assertThat(signUpResponse).isNotNull();
        assertThat(signUpResponse.getToken()).isNotNull();
    }

}