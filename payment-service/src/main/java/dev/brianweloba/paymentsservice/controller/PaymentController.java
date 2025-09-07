package dev.brianweloba.paymentsservice.controller;

import dev.brianweloba.paymentsservice.PaymentsServiceApplication;
import dev.brianweloba.paymentsservice.model.Payment;
import dev.brianweloba.paymentsservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAll(){
        List<Payment> payments = paymentService.getAll();
        if(payments.isEmpty()) return  new ResponseEntity<>("No payments found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(payments,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        try{
            Long idLong = Long.parseLong(id);
            Payment payment = this.paymentService.getPaymentById(idLong);

            if(payment==null) return  new ResponseEntity<>("Payment with id "+id+" not found.",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(payment,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPayment(@RequestBody Payment payment){
        Payment createdPayment = this.paymentService.createPayment(payment);
        return new ResponseEntity<>(createdPayment,HttpStatus.OK);
    }


}
