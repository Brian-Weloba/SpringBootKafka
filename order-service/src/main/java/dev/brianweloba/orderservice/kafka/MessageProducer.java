package dev.brianweloba.orderservice.kafka;

import dev.brianweloba.lib.EventEnvelope;
import dev.brianweloba.lib.OrderCreatedData;
import dev.brianweloba.orderservice.models.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String, EventEnvelope<OrderCreatedData>> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, EventEnvelope<OrderCreatedData>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, EventEnvelope<OrderCreatedData> data){
        kafkaTemplate.send(topic,data);
    }
}
