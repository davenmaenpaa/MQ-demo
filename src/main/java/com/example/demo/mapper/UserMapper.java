package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;

public interface UserMapper {

    static UserDTO entityToDTO(UserEntity entity) {
        return new UserDTO().setUsername(entity.getUsername());
    }

}
