package dev.brianweloba.orderservice.kafka;

import dev.brianweloba.lib.EventEnvelope;
import dev.brianweloba.lib.OrderCreatedData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class MessageProducer {

    private final KafkaTemplate<String, EventEnvelope<OrderCreatedData>> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, EventEnvelope<OrderCreatedData>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, EventEnvelope<OrderCreatedData>>> createOrder(EventEnvelope<OrderCreatedData> data){
        return kafkaTemplate.send("orders.v1", data)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send order event: {}", data, ex);
                        // TODO: Consider implementing retry logic or dead letter queue
                    } else {
                        log.debug("Order event sent: offset={}, partition={}",
                                result.getRecordMetadata().offset(),
                                result.getRecordMetadata().partition());
                    }
                });
    }
}
