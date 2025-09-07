package dev.brianweloba.orderservice.kafka;

import dev.brianweloba.lib.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class MessageProducer {
    private final KafkaTemplate<String, EventEnvelope<?>> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, EventEnvelope<?>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, EventEnvelope<?>>> sendEvent(
            String topic, EventEnvelope<?> data) {
        return kafkaTemplate.send(topic, data)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send event to topic {}: {}", topic, data, ex);
                    } else {
                        log.debug("Event sent to topic {}: offset={}, partition={}",
                                topic,
                                result.getRecordMetadata().offset(),
                                result.getRecordMetadata().partition());
                    }
                });
    }

    public CompletableFuture<SendResult<String, EventEnvelope<?>>> createOrder(
            EventEnvelope<OrderCreatedData> data) {
        return sendEvent("orders.v1", data);
    }

    public CompletableFuture<SendResult<String, EventEnvelope<?>>> confirmOrder(
            EventEnvelope<OrderConfirmedData> data) {
        return sendEvent("orders.v1", data);
    }

    public CompletableFuture<SendResult<String, EventEnvelope<?>>> cancelOrder(
            EventEnvelope<OrderCancelledData> data) {
        return sendEvent("orders.v1", data);
    }

}
