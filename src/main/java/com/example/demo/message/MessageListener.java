package com.example.demo.message;

import com.example.demo.message.dto.Message;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo.model.UserEntity.createEntityFromMessage;

@Slf4j
@Component
public class MessageListener {

    private final UserService service;

    @Autowired
    public MessageListener(UserService service) {
        this.service = service;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "user.create", durable = "true"),
            exchange = @Exchange(value = "user", ignoreDeclarationExceptions = "true",
                                 type = "topic"),
            key = "create"), containerFactory = "factory")
    public void createUser(Message message) {
        log.info("Received Message: {}", message);

        createEntityFromMessage.andThen(service.createUser)
                .apply(message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "user.delete", durable = "true"),
            exchange = @Exchange(value = "user", ignoreDeclarationExceptions = "true",
                                 type = "topic"),
            key = "delete"), containerFactory = "factory")
    public void deleteUser(Message message) {
        log.info("Received Message: {}", message);

        service.deleteByUsername(message.getUser().getUsername());
    }

}
