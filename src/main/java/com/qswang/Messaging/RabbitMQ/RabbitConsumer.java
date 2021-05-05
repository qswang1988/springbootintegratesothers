package com.qswang.Messaging.RabbitMQ;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@PropertySource("classpath:/Messaging/rabbitmq.properties")
//@RabbitListener(queues = "${DirectQueueName}")
public class RabbitConsumer {
    //@RabbitHandler
    @RabbitListener(queues = "${DirectQueueName}")
    public void onMessage(Message message,String msg,Channel channel) throws IOException {
        System.out.println("msg received 1: " + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
