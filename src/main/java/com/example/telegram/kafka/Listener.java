package com.example.telegram.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Listener {

    @KafkaListener(topics = "bot-topic", groupId = "test")
    public void Listener1(String message) {
//        System.out.println("Message: " + message);
         log.info("Message: {}", message);
    }

}
