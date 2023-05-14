package com.example.ampq.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Listener {

    @RabbitListener(queues = AMPQConfig.QUEUE)
    public void listen(String message){
        System.out.println("Received message listener ----->");
        System.out.println(message);
    }

}
