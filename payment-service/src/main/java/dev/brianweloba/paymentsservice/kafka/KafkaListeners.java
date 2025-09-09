package dev.brianweloba.paymentsservice.kafka;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.brianweloba.lib.Enums;
import dev.brianweloba.lib.EventEnvelope;
import dev.brianweloba.lib.OrderCreatedData;
import dev.brianweloba.paymentsservice.model.Payment;
import dev.brianweloba.paymentsservice.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    private final ObjectMapper mapper;
    private final PaymentService paymentService;

    public KafkaListeners(ObjectMapper mapper, PaymentService paymentService) {
        this.mapper = mapper;
        this.paymentService = paymentService;
    }

    @KafkaListener(
            topics = "orders.v1",
            groupId = "payments-svc",
            containerFactory = "orderCreatedFactory"
    )
    public void orderCreatedListener(EventEnvelope<?> event){
        OrderCreatedData data = mapper.convertValue(event.data(),OrderCreatedData.class);

        Payment payment = new Payment();
        payment.setStatus(Enums.PaymentStatus.PENDING);
        payment.setComment("");
        payment.setOrder_id(data.orderId());
        payment.setAmount(data.total());
        payment.setCurrency(data.currency());

        paymentService.createPayment(payment);
    }
}
