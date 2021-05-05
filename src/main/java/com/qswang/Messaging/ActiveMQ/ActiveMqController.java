package com.qswang.Messaging.ActiveMQ;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.Topic;

@RestController
@PropertySource("classpath:/Messaging/activemq.properties")
@RequestMapping("activemq")
public class ActiveMqController {
    @Autowired
    private MsgSender msgSender;

    @Value("${QueueName}")
    private String QueueName;

    @Value("${TopicName}")
    private String TopicName;

    @RequestMapping("/p2p")
    @ResponseBody
    public String test_mq() {
        //point to point msg
        Destination des = new ActiveMQQueue(QueueName);

        for(int i = 1; i <= 10; i++) {
            msgSender.sendMessage(des, "hello"+String.valueOf(i));
        }
        return "ok";
    }

    @RequestMapping("/pubsub")
    @ResponseBody
    public String test_PubSub(){
        // pub sub topic
        Topic topic = new ActiveMQTopic(TopicName);
        for(int i = 1; i <= 3; i++) {
            msgSender.sendMessage(topic, "hello topic "+String.valueOf(i));
            //msgSender.sendTopicMsg(topic, "hello topic "+String.valueOf(i));
        }
        return "ok";
    }

}
