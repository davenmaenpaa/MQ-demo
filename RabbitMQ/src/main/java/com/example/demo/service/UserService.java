package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.UnaryOperator;

@Slf4j
@Service
public class UserService {

    private static final String USER = "user";

    private UserRepository repository;

    @Autowired
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public UnaryOperator<UserEntity> createUser = user -> {
        if (repository.existsByUsername(user.getUsername())) {
            log.info("Username taken: {}", user);
            throw new IllegalArgumentException("Username taken");
        }

        var createdUser = repository.save(user);
        log.info("Saved: {}", createdUser);

        return createdUser;
    };

    public void deleteByUsername(String username) {
        UserEntity userToDelete = repository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(username, USER));

        repository.delete(userToDelete);

        log.info("Deleted: {}", userToDelete);
    }

    public Iterable<UserEntity> findAllUsers() {
        return repository.findAll();
    }

    public UserEntity findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException(username, USER));
    }

}
