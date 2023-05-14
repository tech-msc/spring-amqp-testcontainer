package com.example.ampq;

import com.example.ampq.impl.Publisher;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.ampq.impl.AMPQConfig.*;


public class AmpqApplication {

    public static final String topicExchangeName = "spring-boot-exchange";
    public static final String queueName = "spring-boot";
    public static final String ROUTING_KEY = "foo.bar.#";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    Queue queueExample() {
        return new Queue(QUEUE, true);
    }

    @Bean
    TopicExchange exchangeExample() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding bindingExample() {
        return BindingBuilder.bind(queueExample())
                .to(exchangeExample())
                .with(KEY);
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(com.example.ampq.MessagingRabbitmqApplication.class, args)
        ;
        //.close();
    }

    int count = 1;

    @Bean
    public ApplicationRunner runner(Publisher messageSender) {
        return args -> {
            messageSender.sendMessageToTopic("Hello from RabbitMQ! " + count);
            count++;
            messageSender.sendMessageToTopic("Hello from RabbitMQ! " + count);
            count++;
            messageSender.sendMessageToTopic("Hello from RabbitMQ! " + count);
            count++;
            messageSender.sendMessageToTopic("Hello from RabbitMQ! " + count);
        };
    }

}