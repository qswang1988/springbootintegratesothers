package com.qswang.Messaging.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/Messaging/rabbitmq.properties")
public class RabbitQueue {

    @Value("${DirectExchange}")
    private String exchange;
    @Value("${DirectQueueName}")
    private String queue;
    @Value("${DirectKey}")
    private String key;


    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }

    //@Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(exchange);
    }

    //@Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue directQueue(){
        return new Queue(queue,true);
    }

    @Bean
    public Binding directBinding(@Qualifier("directExchange") Exchange exchange, @Qualifier("directQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(key).noargs();
    }

}
