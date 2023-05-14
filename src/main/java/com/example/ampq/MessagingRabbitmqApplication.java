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

@SpringBootApplication
public class MessagingRabbitmqApplication {

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
        return BindingBuilder.bind(new Queue(QUEUE))
                .to(new TopicExchange(EXCHANGE))
                .with(KEY);
//        return BindingBuilder.bind(queueExample())
//                .to(exchangeExample())
//                .with(KEY);
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

//  @Bean
//  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//      MessageListenerAdapter listenerAdapter) {
//    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//    container.setConnectionFactory(connectionFactory);
//    container.setQueueNames(queueName);
//    container.setMessageListener(listenerAdapter);
//    return container;
//  }

//  @Bean
//  MessageListenerAdapter listenerAdapter(Receiver receiver) {
//    return new MessageListenerAdapter(receiver, "receiveMessage");
//  }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(MessagingRabbitmqApplication.class, args)
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