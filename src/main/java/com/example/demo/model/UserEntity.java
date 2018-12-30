package com.example.demo.model;

import com.example.demo.message.dto.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;
import java.util.function.Function;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true, nullable = false)
    UUID id;

    @NonNull
    @Column(unique = true, nullable = false)
    String username;

    public static Function<Message, UserEntity> createEntityFromMessage =
            message -> new UserEntity().setUsername(message.getUser().getUsername());

}
