package com.qswang.Messaging.ActiveMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Topic;

// data source configured in application.yaml
@Component
public class MsgSender {
    @Resource
    private JmsMessagingTemplate jmsTemplate;

    //transmitting
    public void sendMessage(Destination des, String message) {
        jmsTemplate.convertAndSend(des, message);
    }

    /*public void sendTopicMsg(Topic topic, String message){
        jmsTemplate.convertAndSend(topic,message);
    }*/
}
