package com.example.demo.dto;

import com.example.demo.model.UserEntity;

public interface UserMapper {

    static UserDTO entityToDTO(UserEntity entity) {
        return new UserDTO().setUsername(entity.getUsername());
    }

}
