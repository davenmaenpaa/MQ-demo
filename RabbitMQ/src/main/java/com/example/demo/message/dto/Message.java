package com.example.demo.message.dto;

import com.example.demo.dto.UserDTO;
import com.example.demo.message.Event;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@Accessors(chain = true)
@RequiredArgsConstructor
public class Message {

    @NonNull
    UserDTO user;

    @NonNull
    Event event;

    @NonNull
    Date date;

}
