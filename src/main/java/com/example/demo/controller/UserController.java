package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.message.MessageProducer;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final MessageProducer messageService;

    private final static String PATHVAR_USERNAME = "{username}";

    @Autowired
    public UserController(UserService userService,
                          MessageProducer messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) {
        messageService.sendSendCreateUserMessage(user);

        return user;
    }

    @GetMapping
    public Iterable<UserEntity> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(PATHVAR_USERNAME)
    public UserEntity findUser(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @DeleteMapping(PATHVAR_USERNAME)
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        messageService.sendDeleteUserMessage(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
