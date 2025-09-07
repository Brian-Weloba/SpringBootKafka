package dev.brianweloba.paymentsservice.service;

import dev.brianweloba.paymentsservice.model.Payment;
import dev.brianweloba.paymentsservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentService) {
        this.paymentRepository = paymentService;
    }

    public Payment createPayment(Payment payment){
        return this.paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id){
        return this.paymentRepository.findById(id).orElse(null);
    }

    public List<Payment> getAll(){
        return this.paymentRepository.findAll();
    }
}
