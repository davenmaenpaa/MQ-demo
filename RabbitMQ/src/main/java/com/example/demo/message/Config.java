package com.example.demo.message;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory factory(ConnectionFactory connectionFactory,
                                                        SimpleRabbitListenerContainerFactoryConfigurer configurer) {

        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(jsonMessageConverter());

        configurer.configure(factory, connectionFactory);

        return factory;
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("user");
    }

    @Bean
    public Queue createQueue() {
        return new Queue("user.create");
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue("user.delete");
    }

}
