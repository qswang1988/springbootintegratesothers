package com.qswang.Messaging.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class KafkaController {
    @Autowired
    Sender sender;

    @RequestMapping("/send")
    public String test_send(){
        sender.send("test test test");
        return "ok";
    }

}
