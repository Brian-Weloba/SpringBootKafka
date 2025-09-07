package dev.brianweloba.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(MessageProducer producer){
////        return args -> {
////            OrderCreatedData order = new OrderCreatedData(
////                    UUID.randomUUID(),UUID.randomUUID(),"GBP",1234,
////                    List.of(new OrderItem("SKU-001", 1, 10))
////            );
////            EventEnvelope<OrderCreatedData> event = new EventEnvelope<>(
////                    "order.created",0, Instant.now(), UUID.randomUUID(),order
////            );
////            for(int i =0;i<1_000;i++) producer.createOrder(event);
////        };
//    }
}
