package dev.brianweloba.orderservice.kafka;
import dev.brianweloba.lib.EventEnvelope;
import dev.brianweloba.lib.OrderCreatedData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(
            topics = "orders.v1",
            groupId = "groupId"
    )
    public void listener(EventEnvelope<OrderCreatedData> data){
        System.out.println("Event: "+data.eventType());
    }
}
