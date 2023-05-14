package com.example.ampq;

import com.example.ampq.impl.Listener;
import com.example.ampq.impl.Publisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.util.concurrent.TimeUnit;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
@ExtendWith(OutputCaptureExtension.class) // v2
public class RabbitTest {

    @Container static RabbitMQContainer container =
            new RabbitMQContainer(DockerImageName.parse("rabbitmq:3-management-alpine"));

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", container::getHost);
        registry.add("spring.rabbitmq.port", container::getAmqpPort);
    }

    @Autowired
    Publisher publisher;

    @Autowired
    Listener listener;

    @Test
    void rabbitTest() throws InterruptedException {
        Thread.sleep(5000);
        publisher.sendMessageToTopic("teste");
        Assertions.assertTrue(publisher.getMessages() > 0);
    }

    @Test
    void rabbitTestV2() throws InterruptedException {
        /// TODO msc revisar isso aqui
        final String expectedText = "expected";
        final String sendedText = "expected";

        Thread.sleep(5000);
        publisher.sendMessageToTopic("teste");


        // TODO msc - sera que isso realmente esta lendo da fila do testcontainer?
        await().atMost(5, TimeUnit.SECONDS)
                .until(() -> expectedText.equals(sendedText));


        Assertions.assertTrue(publisher.getMessages() > 0);
    }

}
