package com.example.demo.repository;

import com.example.demo.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

}
