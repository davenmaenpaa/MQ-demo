package com.example.demo.message;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.message.dto.Message;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.demo.message.Event.CREATED;
import static com.example.demo.message.Event.DELETED;

@Slf4j
@Service
public class MessageProducer {


    private AmqpTemplate amqpTemplate;
    private UserRepository repository;

    @Autowired
    public MessageProducer(AmqpTemplate amqpTemplate,
                           UserRepository repository) {
        this.amqpTemplate = amqpTemplate;
        this.repository = repository;
    }

    public void sendSendCreateUserMessage(UserDTO user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User already exist");
        }

        var message = Message.builder()
                .event(CREATED)
                .date(new Date())
                .user(user).build();

        amqpTemplate.convertAndSend("create", message);
        log.info("Sent to rabbit: {}", message);
    }

    public void sendDeleteUserMessage(String username) {
        var message = repository.findByUsername(username)
                .map(UserMapper::entityToDTO)
                .map(user -> Message.builder()
                        .user(user)
                        .event(DELETED)
                        .date(new Date())
                        .build())
                .orElseThrow(IllegalArgumentException::new);

        amqpTemplate.convertAndSend("delete", message);
        log.info("Sent to rabbit: {}", message);
    }

}
