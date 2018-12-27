package com.example.demo.message;

import com.example.demo.message.dto.Message;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo.model.UserEntity.createEntityFromMessage;

@Slf4j
@Component
public class MessageConsumer {

    private final UserService service;

    @Autowired
    public MessageConsumer(UserService service) {
        this.service = service;
    }

    @RabbitListener(queues = "user.create", containerFactory = "factory")
    public void createUser(Message message) {
        log.info("Recieved Message: {}", message);

        createEntityFromMessage.andThen(service.createUser)
                .apply(message);
    }

    @RabbitListener(queues = "user.delete", containerFactory = "factory")
    public void deleteUser(Message message) {
        log.info("Recieved Message: {}", message);

        service.deleteByUsername(message.getUser().getUsername());
    }

}
