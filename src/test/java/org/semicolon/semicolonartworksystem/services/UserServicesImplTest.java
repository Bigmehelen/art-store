package org.semicolon.semicolonartworksystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.semicolon.semicolonartworksystem.data.models.Role;
import org.semicolon.semicolonartworksystem.data.models.User;
import org.semicolon.semicolonartworksystem.data.repositories.UserRepo;
import org.semicolon.semicolonartworksystem.dtos.requests.LoginRequest;
import org.semicolon.semicolonartworksystem.dtos.requests.SignUpRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.LoginResponse;
import org.semicolon.semicolonartworksystem.dtos.responses.SignUpResponse;
import org.semicolon.semicolonartworksystem.exceptions.UserAlreadyExistsException;
import org.semicolon.semicolonartworksystem.utils.JwtUtil;
import org.semicolon.semicolonartworksystem.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.semicolon.semicolonartworksystem.data.models.Role.ADMIN;
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
    public void testThatCanSignUpAsAdminWithCorrectDetails() {
        Mapper.mapToModel(signUpRequest);
        when(userRepo.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(jwtUtil.generateToken(any())).thenReturn("token");
        SignUpResponse signUpResponse = userService.signUpAsAdmin(signUpRequest);
        assertThat(signUpResponse).isNotNull();
        assertThat(signUpResponse.getToken()).isNotNull();
    }

    @Test
    public void testThatCanLoginAsAdminWithCorrectDetails() {
        User user = Mapper.map(request);
        when(userRepo.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any())).thenReturn("token");
        LoginResponse login = userService.loginAsAdmin(request);
        assertThat(login).isNotNull();
        assertThat(login.getToken()).isNotNull();
    }

    @Test
    public void testThatAnExistingUser_buyerCanRegisterAsAdmin() {
        User user = Mapper.mapToModel(signUpRequest);
        user.setRole(Set.of(BUYER));
        when(userRepo.findByEmail(any())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any())).thenReturn("token");
        SignUpResponse signUpResponse = userService.signUpAsAdmin(signUpRequest);

        assertThat(signUpResponse).isNotNull();
        assertThat(signUpResponse.getToken()).isNotNull();

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo, times(1)).save(captor.capture());
        assertThat(captor.getValue().getRole()).isEqualTo(Set.of(BUYER, ADMIN));
    }

    @Test
    public void testThatAnExistingUser_withRoleAsAdminAndBuyerCanThrowException() {
        User user = Mapper.mapToModel(signUpRequest);
        Set<Role> updatedRoles = new HashSet<>(user.getRole());
        updatedRoles.add(Role.ADMIN);
        updatedRoles.add(Role.BUYER);
        user.setRole(updatedRoles);
        when(userRepo.findByEmail(any())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any())).thenReturn("token");

        assertThrows(UserAlreadyExistsException.class, () -> userService.signUpAsAdmin(signUpRequest));

    }

}