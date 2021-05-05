package com.qswang.Messaging.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/Messaging/rabbitmq.properties")
public class RabbitProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Value("${DirectExchange}")
    private String exchange;
    @Value("${DirectKey}")
    private String key;

    public void sendDirectMsg(String message){
        rabbitTemplate.convertAndSend(exchange,key,message);
    }
}
