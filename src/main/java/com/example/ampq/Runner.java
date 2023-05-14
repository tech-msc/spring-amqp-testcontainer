//package com.example.ampq;

//@Component
//public class Runner implements CommandLineRunner {
////
//    @Autowired
//  private RabbitTemplate rabbitTemplate;
//  private final Receiver receiver;
//  private final Listener1 listener1;
////
//  public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
//    this.receiver = receiver;
//    this.rabbitTemplate = rabbitTemplate;
//    listener1 = new Listener1();
//  }
////
//  //@Override
//  public void run(String... args) throws Exception {
//    System.out.println("Sending message...");
//    rabbitTemplate.convertAndSend(
//            MessagingRabbitmqApplication.topicExchangeName,
//            MessagingRabbitmqApplication.ROUTING_KEY,
//            "Hello from RabbitMQ!");
//
//    receiver.getLatch()
//            .await(10000, TimeUnit.MILLISECONDS);
//  }
//
//}