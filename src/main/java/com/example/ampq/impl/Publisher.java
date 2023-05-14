package com.example.ampq.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.ampq.impl.AMPQConfig.EXCHANGE;
import static com.example.ampq.impl.AMPQConfig.KEY;

@Component
public class Publisher {
    @Autowired
    RabbitTemplate template;

    public void sendMessageToTopic(String messageToSend) {
        System.out.println("message to publish: " + messageToSend);
        template.convertAndSend(EXCHANGE, KEY, messageToSend);
        messages.add(messageToSend);
    }


    List<String> messages = new ArrayList<>();

    public int getMessages() {
        return messages.size();
    }

}
