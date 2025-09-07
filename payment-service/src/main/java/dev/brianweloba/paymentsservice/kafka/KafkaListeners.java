package dev.brianweloba.paymentsservice.kafka;
import dev.brianweloba.lib.EventEnvelope;
import dev.brianweloba.lib.OrderCreatedData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(
            topics = "orders.v1",
            groupId = "payments-svc"
    )
    public void listener(EventEnvelope<OrderCreatedData> data){

        System.out.println("Event: "+data.eventType()
        );
    }
}
