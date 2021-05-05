package com.qswang.Messaging.ActiveMQ.SpringConventional;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ActiveMqConfig.class)
public class TestMqSend {
    @Autowired
    ActiveMQQueue activeMQQueue;
    @Autowired
    JmsTemplate jmsTemplate;

    @Test
    public void test_send(){
        for(int i = 0;i<10;i++) {
            String s = String.valueOf(i);
            System.out.println("sent: "+s);
            jmsTemplate.send(activeMQQueue, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage("test"+s);
                    message.setIntProperty("messageCount", 0);
                    return message;
                }
            });
        }
    }


}
