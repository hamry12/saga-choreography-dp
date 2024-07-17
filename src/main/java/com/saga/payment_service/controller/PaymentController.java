package com.saga.payment_service.controller;

import com.saga.payment_service.service.PaymentService;
import org.saga.dto.PaymentRequestDTO;
import org.saga.dto.PaymentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService=paymentService;
    }

    @PostMapping("/processPayment")
    public PaymentResponseDTO processPayment(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return paymentService.processPayment(paymentRequestDTO);
    }

}
