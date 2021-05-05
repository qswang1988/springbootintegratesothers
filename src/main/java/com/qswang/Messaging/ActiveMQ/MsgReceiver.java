package com.qswang.Messaging.ActiveMQ;

import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/Messaging/activemq.properties")
public class MsgReceiver {
    @JmsListener(destination="${QueueName}")
    public void receiveMsg(String text) {
        System.out.println(text+"......");
    }
}
