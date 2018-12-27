package com.example.demo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class UserDTO {

    String username;

}
