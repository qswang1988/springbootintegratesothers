package com.qswang.Messaging.ActiveMQ.SpringConventional;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

@ComponentScan
@Configuration
@PropertySource("classpath:/Messaging/activemq.properties")
public class ConsumerConfig {

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ActiveMQQueue activeMQQueue,QueueListener queueListener, ActiveMQConnectionFactory activeMQConnectionFactory){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setMessageListener(queueListener);
        simpleMessageListenerContainer.setDestination(activeMQQueue);
        simpleMessageListenerContainer.setConnectionFactory(activeMQConnectionFactory);
        return simpleMessageListenerContainer;
    }

}
