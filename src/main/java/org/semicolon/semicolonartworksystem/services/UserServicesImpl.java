package org.semicolon.semicolonartworksystem.services;

import lombok.extern.slf4j.Slf4j;
import org.semicolon.semicolonartworksystem.client.EmailSendingService;
import org.semicolon.semicolonartworksystem.client.dto.EmailRequest;
import org.semicolon.semicolonartworksystem.data.models.Role;
import org.semicolon.semicolonartworksystem.data.models.User;
import org.semicolon.semicolonartworksystem.data.models.UserPrincipal;
import org.semicolon.semicolonartworksystem.data.repositories.UserRepo;
import org.semicolon.semicolonartworksystem.dtos.requests.LoginRequest;
import org.semicolon.semicolonartworksystem.dtos.requests.SignUpRequest;
import org.semicolon.semicolonartworksystem.dtos.responses.LoginResponse;
import org.semicolon.semicolonartworksystem.dtos.responses.SignUpResponse;
import org.semicolon.semicolonartworksystem.exceptions.AdminAlreadyExistsException;
import org.semicolon.semicolonartworksystem.exceptions.AdminNotFoundException;
import org.semicolon.semicolonartworksystem.exceptions.UserAlreadyExistsException;
import org.semicolon.semicolonartworksystem.exceptions.UserNotFoundException;
import org.semicolon.semicolonartworksystem.utils.JwtUtil;
import org.semicolon.semicolonartworksystem.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailSendingService emailSendingService;

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
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(request.getEmail());
        emailRequest.setSubject("welcome to Helen Art Store");
        emailRequest.setBody("Hello " + request.getUserName() + "thank You For Signing Up");
        emailSendingService.sendEmail(emailRequest);
        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setToken(jwtUtil.generateToken(new UserPrincipal(saved)));
        return signUpResponse;
    }

    @Override
    public Long inventory() {
        return userRepo.count();
    }

    @Override
    @Transactional
    public SignUpResponse signUpAsAdmin(SignUpRequest request) {
        Optional<User> foundUser = userRepo.findByEmail(request.getEmail());

        if(foundUser.isPresent() &&
                foundUser.get().getRole().contains(Role.ADMIN)
                && foundUser.get().getRole().contains(Role.BUYER)){
            throw new UserAlreadyExistsException("User with email" + request.getEmail() + "already exists");
        }
        if(foundUser.isEmpty()) {
            User user = Mapper.mapToModel(request);
            user.setRole(Set.of(Role.ADMIN));
            userRepo.save(user);
            String token = jwtUtil.generateToken(new UserPrincipal(user));
            SignUpResponse signUpResponse = new SignUpResponse();
            signUpResponse.setToken(token);
            return signUpResponse;
        } else {
            Set<Role> updatedRoles = new HashSet<>(foundUser.get().getRole());
            updatedRoles.add(Role.ADMIN);
            foundUser.get().setRole(updatedRoles);
            foundUser.get().setPassword(request.getPassword());
            foundUser.get().setUserName(request.getUserName());
            userRepo.save(foundUser.get());

            SignUpResponse signUpResponse = new SignUpResponse();
            String token  = jwtUtil.generateToken(new UserPrincipal(foundUser.get()));
            signUpResponse.setToken(token);
            return signUpResponse;
        }
    }

    @Override
    public LoginResponse loginAsAdmin(LoginRequest request) {
        User user = Mapper.map(request);
        User saved = userRepo.findByEmail(user.getEmail())
                .orElseThrow(()-> new AdminNotFoundException("Admin not found"));
        user.setRole(Set.of(Role.ADMIN));
        UserPrincipal  userPrincipal = new UserPrincipal(saved);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtUtil.generateToken(userPrincipal));
        return loginResponse;
    }

}
