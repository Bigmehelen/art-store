package org.semicolon.semicolonartworksystem.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semicolon.semicolonartworksystem.data.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepoTest {
    User user;

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUserName("avatar");
        user.setEmail("avatar@gmail.com");
        user.setPassword("buyer");
        user.setPassword("pass12345");
    }

    @Test
    public void testThatUserCanBeAdded() {
        User saved = userRepo.save(user);
        assertNotNull(saved);

    }

}