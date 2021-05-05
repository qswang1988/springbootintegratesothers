package com.qswang.Messaging.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaSubscriber {
    @KafkaListener(topics = "${app.topic}")
    public void receive(@Payload String message, @Headers MessageHeaders headers) {
        System.out.println("sub one: "+message);
    }
}
