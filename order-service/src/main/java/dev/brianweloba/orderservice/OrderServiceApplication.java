package dev.brianweloba.orderservice;

import dev.brianweloba.lib.EventEnvelope;
import dev.brianweloba.lib.OrderCreatedData;
import dev.brianweloba.lib.OrderItem;
import dev.brianweloba.orderservice.kafka.MessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MessageProducer producer){
        return args -> {
            OrderCreatedData order = new OrderCreatedData(
                    UUID.randomUUID(),UUID.randomUUID(),"GBP",1234,
                    List.of(new OrderItem("SKU-001", 1, 10))
            );
            EventEnvelope<OrderCreatedData> event = new EventEnvelope<>(
                    "order.created",0, Instant.now(), UUID.randomUUID(),order
            );
            for(int i =0;i<1_000;i++) producer.sendMessage("orders.v1",event);
        };
    }
}
