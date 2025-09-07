package dev.brianweloba.orderservice.kafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener
    public void listener(){

    }
}
