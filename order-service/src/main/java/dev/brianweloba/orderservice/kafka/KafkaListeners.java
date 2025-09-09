package dev.brianweloba.orderservice.kafka;
import dev.brianweloba.lib.EventEnvelope;
import dev.brianweloba.lib.OrderCreatedData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(
            topics = "payments.v1",
            groupId = "orders-svc"
    )
    public void listener(EventEnvelope<OrderCreatedData> data){
        System.out.println("Event: "+data.toString());
    }
}
