package com.qswang.Messaging.RabbitMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rabbitmq")
public class RabbitController {
    @Autowired
    RabbitProducer rabbitProducer;

    @RequestMapping("/direct")
    @ResponseBody
    public String send(){
        rabbitProducer.sendDirectMsg("test direct msg");
        return "ok";
    }

}
