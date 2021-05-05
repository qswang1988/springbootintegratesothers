package com.qswang.Messaging.ActiveMQ.SpringConventional;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@PropertySource("classpath:/Messaging/activemq.properties")
public class ActiveMqConfig {

    @Value("${BrokerURL}")
    String BrokerURL;
    @Value("${QueueName}")
    String QueueName;

    @Bean
    public ActiveMQQueue activeMQQueue(){
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(QueueName);
        return activeMQQueue;
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                BrokerURL);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        /*SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory(connectionFactory);
        jmsTemplate.setConnectionFactory(singleConnectionFactory);*/
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        return jmsTemplate;
    }




}
