package com.qswang.Messaging.ActiveMQ.SpringConventional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Component
public class QueueListener implements MessageListener {

    public void onMessage(Message message) {
        if(message instanceof MapMessage){

        }else if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage)message;
            try {
                System.out.println("received: "+textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else if(message instanceof BytesMessage){

        }else{
            System.out.println("...");
        }
    }
}
