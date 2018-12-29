package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceSpringTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Test
    @Ignore
    void testCreateUser() {
        service.createUser.apply(new UserEntity().setUsername("Daven"));
    }

}