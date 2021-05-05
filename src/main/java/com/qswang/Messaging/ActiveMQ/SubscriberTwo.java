package com.qswang.Messaging.ActiveMQ;

import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/Messaging/activemq.properties")
public class SubscriberTwo {

    @JmsListener(destination="${TopicName}")
    public void subscriberOne(String text) {
        System.out.println(text+" sub2......");
    }

}
