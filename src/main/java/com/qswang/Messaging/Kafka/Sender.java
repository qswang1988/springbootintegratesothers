package com.qswang.Messaging.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class Sender {
    //private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${app.topic}")
    private String topic;

    public void send(String message){
        //LOG.info("sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }

}
